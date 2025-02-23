package com.example.binding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.binding.databinding.ActivityMain9Binding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity9 : AppCompatActivity() {
    private lateinit var binding : ActivityMain9Binding
    private lateinit var db : FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain9Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        db = FirebaseFirestore.getInstance()
        val koleksi = listOf("sarana divre 1", "sarana daop 1")

        binding.updateperawatan.setOnClickListener {
            val nomorSarana = binding.inputnomorsarana2.text.toString()
            val jenisPerawatan = binding.inputjenisperawatan2.text.toString()
            val tanggalPerawatan = binding.inputtanggalperawatan.text.toString()

            if (nomorSarana.isEmpty() || jenisPerawatan.isEmpty() || tanggalPerawatan.isEmpty()) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var dataFound = false
            var collectionsProcessed = 0
            val totalCollections = koleksi.count()

            for (koleksi in koleksi) {
                db.collection(koleksi)
                    .whereEqualTo("a", nomorSarana)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            dataFound = true
                            for (document in documents) {
                                val docId = document.id
                                db.collection(koleksi)
                                    .document(docId)
                                    .update(
                                        mapOf(
                                            "c" to tanggalPerawatan,
                                            "d" to jenisPerawatan
                                        )
                                    )
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Data berhasil diupdate")
                                        Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()

                                        val updatedText = "Nomor sarana yang diupdate: $nomorSarana"
                                        binding.updatednomorsarana2.append("$updatedText\n") // Tambahkan nomor ke TextView
                                        binding.updatednomorsarana2.visibility = View.VISIBLE

                                        // Kosongkan input setelah update
                                        binding.inputnomorsarana2.text.clear()
                                        binding.inputjenisperawatan2.text.clear()
                                        binding.inputtanggalperawatan.text.clear()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w("Firestore", "Gagal mengupdate di $koleksi", e)
                                        Toast.makeText(this, "Gagal mengupdate di $koleksi", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }

                        collectionsProcessed++
                        if (collectionsProcessed == totalCollections) {
                            if (!dataFound) {
                                Toast.makeText(this, "Nomor Sarana tidak ditemukan/ Periksa Penulisan Nomor Sarana", Toast.LENGTH_SHORT).show()
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