package com.example.binding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.binding.databinding.ActivityLupaPasswordBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LupaPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLupaPasswordBinding
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLupaPasswordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        binding.tombolresetpassword.setOnClickListener {
            val email = binding.emaillupapassword.text.toString()

            if (email.isEmpty()) {
                binding.emaillupapassword.error="Email harus diisi"
                binding.emaillupapassword.requestFocus()
                return@setOnClickListener
            }

            ResetPassword (email)


        }










        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun ResetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Reset Password Telah Dikirim", Toast.LENGTH_SHORT).show()
                    val intent = Intent (this,MainActivity::class.java)
                    startActivity(intent)
                }

                else {
                    Toast.makeText(this,"Reset Password Gagal", Toast.LENGTH_SHORT).show()
                }

            }


    }
}