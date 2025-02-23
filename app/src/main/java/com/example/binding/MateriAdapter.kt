package com.example.binding

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.net.URI

class MateriAdapter (private val userList: ArrayList<Materi>,private val onItemClick: (String) -> Unit ): RecyclerView.Adapter<MateriAdapter.MyViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user,parent,false)
        return MyViewHolder(itemView)




    }

    override fun onBindViewHolder(holder: MateriAdapter.MyViewHolder, position: Int) {
        val materi = userList[position]
        holder.judul.text = materi.judul
        holder.itemView.setOnClickListener {
            if (materi.pdfUrl.isNullOrEmpty()){
                Toast.makeText(holder.itemView.context,"PDF tidak ditemukan",Toast.LENGTH_SHORT).show()
            }
            else {
                onItemClick(materi.pdfUrl)}

        }



    }

    override fun getItemCount(): Int {


       return userList.size
    }




    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val judul : TextView = itemView.findViewById(R.id.materi)







    }


}