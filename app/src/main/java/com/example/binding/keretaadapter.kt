package com.example.binding

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class keretaadapter (private val userlist : ArrayList<kereta>,) : RecyclerView.Adapter<keretaadapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): keretaadapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.keretadivre1,parent,false)
        return MyViewHolder(itemView)








    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val kereta= userlist[position]
        holder.depo.text = kereta.a
        holder.jenis.text = kereta.b
        holder.nomor.text = kereta.c
        holder.tanggal.text = kereta.d

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("a",kereta.a)
            intent.putExtra("b",kereta.b)
            intent.putExtra("c",kereta.c)
            intent.putExtra("d",kereta.d)
            intent.putExtra("e",kereta.e)
            intent.putExtra("f",kereta.f)
            intent.putExtra("g",kereta.g)
            intent.putExtra("h",kereta.h)
            intent.putExtra("i",kereta.i)
            intent.putExtra("j",kereta.j)
            context.startActivity(intent)


        }


    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val depo : TextView = itemView.findViewById(R.id.tvdepo)
        val jenis : TextView = itemView.findViewById(R.id.tvjenis)
        val nomor : TextView = itemView.findViewById(R.id.tvnomor)
        val tanggal : TextView = itemView.findViewById(R.id.tvtanggal)




    }


}