package com.example.doctorconsult

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class ViewAdapter (options : FirebaseRecyclerOptions<Viewmodel>, var c : Context) : FirebaseRecyclerAdapter<Viewmodel, ViewAdapter.Viewholder>(options){

    var con = c

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.viewdoctors,parent,false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int, model: Viewmodel) {

        holder.name.setText(model.Name)
        holder.degree.setText(model.CollageDegree)
        Glide.with(holder.images.context).load(model.Image).into(holder.images)
        holder.next.setOnClickListener {

            var id = getRef(position).key.toString()
            var i = Intent(con,DoctorDetails::class.java)
            i.putExtra("id",id)
            con.startActivity(i)


        }
    }


    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview){


        var name = itemview.findViewById<TextView>(R.id.nametext)
        var images = itemview.findViewById<ImageView>(R.id.imgs)
        var next = itemview.findViewById<ImageView>(R.id.next)
        var degree = itemview.findViewById<TextView>(R.id.degreeview)

}



}