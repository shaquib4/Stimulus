package com.example.stimulus

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import de.hdodenhof.circleimageview.CircleImageView

class SignUpActivity : AppCompatActivity() {
    private lateinit var profileImage: CircleImageView
    private  var filepath: Uri? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        profileImage=findViewById(R.id.profileImage)
        profileImage.setOnClickListener {
            selectImage()
        }
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            filepath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            profileImage.setImageBitmap(bitmap)
        }
    }
}