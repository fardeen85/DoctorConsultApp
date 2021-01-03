package com.example.doctorconsult

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.dialgue.*
import kotlinx.android.synthetic.main.dialgue.view.*
import java.util.*
import kotlin.collections.HashMap

class PatientsAdapter (options : FirebaseRecyclerOptions<PatientModel>, var c : Context,val names : String) : FirebaseRecyclerAdapter<PatientModel,PatientsAdapter.Viewholder>(options){

     lateinit var auth :FirebaseAuth
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.patientview,parent,false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int, model: PatientModel) {

        auth = FirebaseAuth.getInstance()

        //hidden id textview invisible which contains patients id for doctor to use and send prescriptions to the requested patient

       holder.hiddentextview.visibility = View.INVISIBLE

        holder.name.setText(model.Names)
        holder.email.setText(model.Email)
        holder.phone.setText(model.Phone_no)
        holder.age.setText(model.Age)
        holder.hiddentextview.setText(model.hid.toString())

        //close presription dialogue

        holder.imageclose.setOnClickListener {

            val ad = AlertDialog.Builder(c)
            ad.setTitle("Remove")
            ad.setMessage("Patient will be removed")
            ad.setIcon(android.R.drawable.ic_delete)
            ad.setPositiveButton("Yes",{DialogInterface, i ->

                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Mypatients").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).removeValue().addOnFailureListener{

                    Toast.makeText(c,"PatientRemoved",Toast.LENGTH_SHORT).show()
                }
            })

            ad.setNegativeButton("No",{DialogInterface, i ->


            })

            ad.show()
        }


        //start dialgue for adding prescription

        holder.addprescription.setOnClickListener {

            val prescriptiondialogue = Dialog(c,R.style.Theme_AppCompat_Light_DialogWhenLarge)
            val inf = LayoutInflater.from(c).inflate(R.layout.dialgue,null)
            prescriptiondialogue.setContentView(R.layout.dialgue)
            var name = prescriptiondialogue.patientsname.setText(model.Names.toString())
            var age = prescriptiondialogue.patientage.setText(model.Age)


            //send prescription to patient
            var sendbutton = prescriptiondialogue.btnsend.setOnClickListener{

                var prescriptions = prescriptiondialogue.txtprescription.text.toString()

                var values = HashMap<Any,String>()
                values.put("name",prescriptiondialogue.patientsname.text.toString())
                values.put("age",prescriptiondialogue.patientage.text.toString())
                values.put("Dname",names.toString())
                values.put("prescription",prescriptions)
                values.put("date",prescriptiondialogue.txtdateinput.text.toString())
                val key = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Prescriptions").push().key.toString()
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Prescriptions").child(holder.hiddentextview.text.toString()).child(key).setValue(values).addOnSuccessListener {

                    Toast.makeText(c,"Prescription sent..:)",Toast.LENGTH_SHORT).show()
                    prescriptiondialogue.dismiss()

                }
            }

            //close dialgue

            prescriptiondialogue.findViewById<ImageView>(R.id.closeimage).setOnClickListener {

                prescriptiondialogue.dismiss()
            }





            prescriptiondialogue.show()

        }


    }


    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview){


        var name = itemview.findViewById<TextView>(R.id.patientsname)
        var email = itemview.findViewById<TextView>(R.id.patientemail)
        var phone = itemview.findViewById<TextView>(R.id.phoneno)
        var imageclose = itemview.findViewById<ImageView>(R.id.imgclose)
        var hiddentextview = itemview.findViewById<TextView>(R.id.hiddentextview)
        var addprescription = itemview.findViewById<ImageView>(R.id.imgmedicine)
        var age : TextView = itemview.findViewById(R.id.age1)


    }


}