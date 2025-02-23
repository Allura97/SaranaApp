package com.example.binding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.ticker
import java.net.IDN

class MainActivity6 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<kereta>
    private lateinit var adapter: keretaadapter
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main6)
        recyclerView = findViewById(R.id.recyclerviewkereta)
        userList = ArrayList()
        adapter = keretaadapter(userList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)












        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }






         val db = FirebaseFirestore.getInstance()
         db.collection("sarana divre 1")
             .get()
             .addOnSuccessListener { document ->
                 val tempList = mutableListOf<kereta>()
                 for (document in document) {
                     val kereta = document.toObject(kereta::class.java)

                     tempList.add(kereta)
          }
          tempList.sortWith(compareBy<kereta>
         {it.a}
         .thenBy
         {it.b}
          .thenBy
          {it.c}
          .thenBy
         {it.d})
         userList.clear()
         userList.addAll(tempList)
         adapter.notifyDataSetChanged()
                 }
             .addOnFailureListener { exception ->
                 Log.w("MainActivity", "Error getting documents.", exception)





    }
        fun onKeretaClicked(kereta: kereta) {
            // Membuka DetailActivity dan mengirimkan item ID
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("a", kereta.a) // Misalnya id adalah atribut yang ada di kelas kereta
            startActivity(intent)
        }



    }}
