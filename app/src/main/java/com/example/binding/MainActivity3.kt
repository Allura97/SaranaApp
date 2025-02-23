package com.example.binding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.binding.databinding.ActivityMain2Binding
import com.example.binding.databinding.ActivityMain3Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding : ActivityMain3Binding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    var currentpage = "dashboard"

    //lateinit var user : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        getUserData ()

        showWelcomeMessage()









        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }





        //if (user.currentUser != null){
          //  user.currentUser?.let {
               // binding.user.text = it.displayName
           // }
      //  }



        binding.logout.setOnClickListener{
            auth.signOut()
            Toast.makeText(this, "Anda telah Logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()


        }


        binding.tombolbelajar1.setOnClickListener{
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }
        binding.tombollaporan.setOnClickListener{
            val intent = Intent(this, MainActivity8::class.java)
            startActivity(intent)
        }


        binding.tomboldaftarsarana1.setOnClickListener{
            val intent = Intent(this, MainActivity5::class.java)
            startActivity(intent)
        }


    }





    private fun getUserData() {
        val user = auth.currentUser
        if (user != null) {
            val uid = user.uid // Ambil UID pengguna
            // Ambil data dari Firestore
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Ambil nama dan NIPP dari Firestore
                        val nama = document.getString("nama") // Ambil nilai nama dari Firestore
                        val nipp = document.getString("nipp") // Ambil nilai nipp dari Firestore

                        // Tampilkan nama dan NIPP di TextView menggunakan binding
                        binding.user.text = nama ?: "Nama tidak tersedia" // Menangani null
                        binding.tvnipp.text = nipp ?: "NIPP tidak tersedia" // Menangani null
                    } else {
                        binding.user.text = "Dokumen tidak ditemukan"
                        binding.tvnipp.text = "Dokumen tidak ditemukan"
                    }
                }
                .addOnFailureListener { exception ->
                    binding.user.text = "Error: ${exception.message}"
                    binding.tvnipp.text = "Error: ${exception.message}"
                }
        } else {
            binding.user.text = "Pengguna tidak terautentikasi"
            binding.tvnipp.text = "Pengguna tidak terautentikasi"
        }
    }


    private fun showWelcomeMessage() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val message = when {
            hourOfDay in 0..11 -> "Selamat Pagi"
            hourOfDay in 12..17 -> "Selamat Siang"
            else -> "Selamat Malam"
        }
        binding.selamatdatang.text = message
    }

    override fun onBackPressed (){
        if (currentpage == "dashboard") {
            Toast.makeText(this, "Keluar gunakan tombol logout", Toast.LENGTH_SHORT).show()
        }
        else {
            super.onBackPressed()

        }


    }


}
