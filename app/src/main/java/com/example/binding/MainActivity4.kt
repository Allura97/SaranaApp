package com.example.binding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity4 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<Materi>
    private lateinit var adapter: MateriAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)
        recyclerView = findViewById(R.id.recyclerview)
        userList = ArrayList()
        adapter = MateriAdapter(userList) { pdfUrl ->
            openPDF(pdfUrl)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)




















        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val db = FirebaseFirestore.getInstance()
        db.collection("materi")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val materi = document.toObject(Materi::class.java)
                    userList.add(materi)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("MainActivity4", "Tidak dapat menampilkan data ", exception)

            }
















    }

    private fun openPDF(pdfUrl: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val pdfUri = Uri.parse(pdfUrl)
            intent.setDataAndType(pdfUri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)




        } catch (e: Exception) {
            Toast.makeText(this, "Gagal membuka PDF", Toast.LENGTH_SHORT).show()
        }






    }


}