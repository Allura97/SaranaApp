package com.example.binding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.binding.databinding.ActivityMain7Binding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity7 : AppCompatActivity() {
    private lateinit var binding : ActivityMain7Binding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain7Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        val koleksi = listOf("sarana divre 1", "sarana daop 1")

        binding.updateposisisarana.setOnClickListener {
            val nomorsarana = binding.inputnomorsarana.text.toString()
            val inputposisisarana = binding.inputposisisarana.text.toString()

            // Validasi input untuk string kosong
            if (nomorsarana.isEmpty() || inputposisisarana.isEmpty()) {
                Toast.makeText(this, "Masukkan nomor dan posisi sarana yang valid.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Memisahkan nomor sarana jika ada lebih dari satu
            val nomorsaranaList = nomorsarana.split(",").map { it.trim() }

            var dataFound = false
            var collectionsProcessed = 0
            val totalCollections = koleksi.count()

            for (koleksi in koleksi) {
                Log.d("Firestore", "Mencari dokumen dengan a = $nomorsarana di koleksi $koleksi")

                db.collection(koleksi)
                    .whereEqualTo("a", nomorsarana)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            dataFound = true
                            for (document in documents) {
                                db.collection(koleksi)
                                    .document(document.id)
                                    .update("e", inputposisisarana)
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Data berhasil diupdate")
                                        Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

                                        val updatedText = "Nomor sarana yang diupdate: $nomorsarana"
                                        binding.updatednomorsarana.append("$updatedText\n") // Tambahkan nomor ke TextView
                                        binding.updatednomorsarana.visibility = View.VISIBLE

                                        // Kosongkan input setelah update
                                        binding.inputnomorsarana.text.clear()
                                        binding.inputposisisarana.text.clear()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("Firestore", "Gagal mengupdate di $koleksi", e)
                                    }
                            }
                        }

                        collectionsProcessed++
                        if (collectionsProcessed == totalCollections) {
                            if (!dataFound) {
                                Toast.makeText(this, "Nomor Sarana ditemukan/ Periksa Penulisan Nomor Sarana", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Gagal mengambil data di $koleksi", e)
                        collectionsProcessed++ // Pastikan ini juga dihitung
                        if (collectionsProcessed == totalCollections) {
                            Toast.makeText(this, "Nomor Sarana tidak ditemukan/ Periksa Penulisan Nomor Sarana", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}