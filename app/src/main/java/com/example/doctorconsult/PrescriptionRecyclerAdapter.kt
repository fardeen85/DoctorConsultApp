package com.example.doctorconsult

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.customviewappointments.view.*
import kotlinx.android.synthetic.main.dialgue.*

class PrescriptionRecyclerAdapter (val c : Context, var options: FirebaseRecyclerOptions<Prescriptionmodel>,val dname : String) : FirebaseRecyclerAdapter<Prescriptionmodel,PrescriptionRecyclerAdapter.Viewholder>(options){

    lateinit var auth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inf = LayoutInflater.from(c).inflate(R.layout.view_prescriptions_list,null)
        return Viewholder(inf)

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int, model: Prescriptionmodel) {

        auth = FirebaseAuth.getInstance()


        holder.nameofpatient.setText(model.name.toString())
        holder.patientage.setText(model.age.toString())
        holder.prescriptiontext.setText(model.prescription)
        holder.dname.setText(model.Dname.toString())
        holder.date.setText(model.date)

        //to delete prescription
        holder.delete.setOnClickListener {

            val a= AlertDialog.Builder(c)
            a.setTitle("Confirm remove")
            a.setMessage("this prescription will be removed")
            a.setPositiveButton("Ok",{DialogInterface, i ->

                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Prescriptions").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).removeValue().addOnFailureListener{

                    Toast.makeText(c,"Something went wrong",Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {

                    Toast.makeText(c,"Prescription removed",Toast.LENGTH_SHORT).show()

                }

            })

            a.setNegativeButton("No",{DialogInterface, i ->

                a.setCancelable(true)
            })

            a.show()


        }

         holder.prescriptiontext.setMovementMethod(ScrollingMovementMethod())

    }


    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val nameofpatient = itemview.findViewById<TextView>(R.id.pnamedisplay)
        val patientage = itemview.findViewById<TextView>(R.id.pagedisplay)
        val prescriptiontext = itemview.findViewById<TextView>(R.id.presdisplay)
        val date = itemview.findViewById<TextView>(R.id.datedisplay)
        var dname = itemview.findViewById<TextView>(R.id.dispaymname)
        val delete = itemview.findViewById<ImageView>(R.id.delete_prescribe)
    }
}