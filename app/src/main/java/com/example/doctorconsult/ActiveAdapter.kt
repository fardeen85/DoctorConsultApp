package com.example.doctorconsult

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActiveAdapter(val c : Context, options : FirebaseRecyclerOptions<ActiveModel>) : FirebaseRecyclerAdapter<ActiveModel,ActiveAdapter.ViewHolder>(options){

     lateinit var auth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view  = LayoutInflater.from(c).inflate(R.layout.active_appointments,parent,false)
        return  ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ActiveModel) {

        auth = FirebaseAuth.getInstance()

        holder.with.setText(model.With.toString())
        holder.date.setText(model.date.toString())
        holder.time.setText(model.time.toString())
        holder.dissmiss.setOnClickListener {

            val a = AlertDialog.Builder(c)
            a.setTitle("Confirm Dissmiss")
            a.setMessage("Are you sure you want to dissmiss it")
            a.setPositiveButton("Yes",{DialogInterface, i ->


                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ActiveAppointments").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).removeValue().addOnCompleteListener{

                    Toast.makeText(c,"Deleted",Toast.LENGTH_SHORT).show()
                }

            })

            a.setNegativeButton("No",{DialogInterface, i ->

                a.setCancelable(true)
            })

            a.show()

        }


    }


    class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val with : TextView = itemview.findViewById(R.id.drname)
        val date : TextView = itemview.findViewById(R.id.drdate)
        val dissmiss : TextView = itemview.findViewById(R.id.dissmiss)
        var time : TextView = itemview.findViewById(R.id.drtime)
    }
}