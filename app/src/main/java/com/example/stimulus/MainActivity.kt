package com.example.stimulus


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider



class MainActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 120
    }

    lateinit var signIn: ImageView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    lateinit var signUp: TextView
    lateinit var signUpGoogle: ImageView
    lateinit var logIn: Button
    lateinit var email: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* mAuth = FirebaseAuth.getInstance()
         val user = mAuth.currentUser
         Handler().postDelayed({
             if(user != null){
                 val dashboardIntent = Intent(this, DashboardActivity::class.java)
                 startActivity(dashboardIntent)
             }else{
                 val signInIntent = Intent(this, SignInActivity::class.java)
                 startActivity(signInIntent)
             }
         },
             2000
         )*/
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()
        signIn = findViewById(R.id.sign_in_btn)
        signIn.setOnClickListener {
            signIn()
        }
        auth = FirebaseAuth.getInstance()
        signUp = findViewById(R.id.btn_sign_up)
        logIn = findViewById(R.id.btnLogin)
        email = findViewById(R.id.edtemail)
        password = findViewById(R.id.edtpass)
        signUpGoogle = findViewById(R.id.sign_in_btn)

        signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        logIn.setOnClickListener {
            doLogin()
        }

        signUpGoogle.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SignInActivity.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == SignInActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("signInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("signInActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, SongsList::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)

                }
            }
    }

    private fun doLogin() {
        if (email.text.toString().isEmpty()) {
            email.error = "Please Enter email"
            email.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "please enter valid email"
            email.requestFocus()
            return
        }
        if (password.text.toString().isEmpty()) {
            email.error = "Please Enter password"
            email.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    updateUI(user)
                } else {


                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, SongsList::class.java))
        }


    }
}