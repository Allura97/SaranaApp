package com.example.binding

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        db = FirebaseFirestore.getInstance()
        val keretaA = intent.getStringExtra("a")
        val keretaB = intent.getStringExtra("b")
        val keretaC = intent.getStringExtra("c")
        val keretaD = intent.getStringExtra("d")
        val posisirealtime = intent.getStringExtra("e")
        val riwayatgangguan1 = intent.getStringExtra("f")
        val riwayatGangguan2 = intent.getStringExtra("g")
        val riwayatGangguan3 = intent.getStringExtra("h")
        val riwayatGangguan4 = intent.getStringExtra("i")
        val riwayatGangguan5 = intent.getStringExtra("j")


















        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (keretaA != null) {
            db.collection("sarana divre 1").document(keretaA).get()
                .addOnSuccessListener{documentsnapshot ->
                    val kereta = documentsnapshot.toObject(kereta::class.java)
                    if (kereta != null) {
                        findViewById<TextView>(R.id.tvnomorsarana).text = kereta.a
                        findViewById<TextView>(R.id.tvdepodidetil).text = kereta.b
                        findViewById<TextView>(R.id.tvtanggaldidetil).text = kereta.c
                        findViewById<TextView>(R.id.tvterakhirperawatan).text = kereta.d
                        findViewById<TextView>(R.id.tvposisirealtime).text = kereta.e
                        findViewById<TextView>(R.id.tvriwayatgangguan1).text = kereta.f
                        findViewById<TextView>(R.id.tvriwayatgangguan2).text = kereta.g
                        findViewById<TextView>(R.id.tvriwayatgangguan3).text = kereta.h
                        findViewById<TextView>(R.id.tvriwayatgangguan4).text = kereta.i
                        findViewById<TextView>(R.id.tvriwayatgangguan5).text = kereta.j

                    }


                }
                .addOnFailureListener{exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }



        }





    }



    }

