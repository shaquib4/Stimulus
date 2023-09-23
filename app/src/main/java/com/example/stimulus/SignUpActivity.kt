package com.example.stimulus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
lateinit var signIn : TextView
    private lateinit var auth: FirebaseAuth
    lateinit var signUp: Button
    lateinit var email: EditText
    lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        signUp = findViewById(R.id.btnSignUp)
        email = findViewById(R.id.edt_email)
        password = findViewById(R.id.edt_pass)
signIn = findViewById(R.id.txtSignIn)
        signIn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        signUp.setOnClickListener {
            signUpUser()
        }
    }
    private fun signUpUser(){
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
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {

                    Toast.makeText(baseContext, "SignUp Failed",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}