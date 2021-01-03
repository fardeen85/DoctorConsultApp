package com.example.doctorconsult

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class historyadapter(val c : Context,  options: FirebaseRecyclerOptions<Phistorymodel>) :  FirebaseRecyclerAdapter<Phistorymodel,historyadapter.Viewholder>(options) {

    lateinit var auth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inf = LayoutInflater.from(parent.context).inflate(R.layout.historylist,parent,false)
        return Viewholder(inf)

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int, model: Phistorymodel) {

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser!!.uid

        holder.dname.setText(model.dname.toString())


        holder.delete.setOnClickListener {

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("PatientHistoreis").child(user.toString()).child(getRef(position).key.toString()).removeValue().addOnCompleteListener{

                Toast.makeText(c,"Deleted",Toast.LENGTH_SHORT).show()
            }
        }


    }

    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val delete : ImageView = itemview.findViewById(R.id.cross) as ImageView
        val dname : TextView = itemview.findViewById(R.id.dname) as TextView
    }

}