package com.example.doctorconsult

import android.app.AlertDialog
import android.app.Notification
import android.content.Context
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseAdapter(options : FirebaseRecyclerOptions<Model>,val c : Context,val name : String) : FirebaseRecyclerAdapter<Model,FirebaseAdapter.Viewholder>(options){

    var acceted : Boolean = false
    var id = 0
    lateinit var auth : FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customviewappointments, parent, false)

        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int, model: Model) {

        auth = FirebaseAuth.getInstance()

        //Make textview of id invisible

        holder.hid.visibility = View.INVISIBLE
        holder.accept.visibility = View.VISIBLE

        //bind views with data

        holder.cancelbutton.visibility = View.INVISIBLE
        holder.name.setText(model.Name.toString())
        holder.email.setText(model.Email.toString())
        holder.phoneno.setText(model.phone.toString())
        holder.timing.setText(model.Time.toString())
        holder.Datetext.setText(model.Date.toString())
        holder.accept.setText(model.status.toString())
        holder.hid.setText(model.hid.toString())
        holder.ageview.setText(model.Age.toString())



        //if accepted button is pressed it changes the text of button by loading status from firebase to buttons text and if it is accepted then button will be invisible
        //chakcs every time when it is load and view are bind
        val buttonstatus = holder.accept.text.toString()

        if (buttonstatus == "Accepted"){

            holder.accept.visibility = View.INVISIBLE
        }




        //request cancel button
        holder.cancel.setOnClickListener {


            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).removeValue()
        }

        //accept button pressed send data to firebase to doctors my pateints node and the requested patient to let him know the appointment is active
        holder.accept.setOnClickListener {

            val key = FirebaseDatabase.getInstance().getReference().child("ConsultApp").push().key
            val key2 = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").push().key


            var putstatus = HashMap<String,Any>()
            putstatus.put("status","Accepted")
            acceted == true
            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).updateChildren(putstatus)
            var items = HashMap<String,Any>()
            items.put("With",name.toString())
            items.put("date",holder.Datetext.text.toString())
            items.put("time",holder.timing.text.toString())
            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ActiveAppointments").child(holder.hid.text.toString()).child(key.toString()).setValue(items).addOnFailureListener{


            }.addOnSuccessListener {


                Toast.makeText(c,"Request Accepted",Toast.LENGTH_SHORT).show()
                var items2 = HashMap<String,Any>()
                items2.put("Names",holder.name.text.toString())
                items2.put("Phone_no",holder.phoneno.text.toString())
                items2.put("Email",holder.email.text.toString())
                items2.put("hid",holder.hid.text.toString())
                items2.put("Age",holder.ageview.text.toString())
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Mypatients").child(auth.currentUser!!.uid.toString()).child(key2.toString()).setValue(items2).addOnFailureListener{

                }

            }



        }

        //show a alert dialgue for confirmation to delete the button

        holder.cancel.setOnClickListener {

            //AlertDialogue
            var a = AlertDialog.Builder(c)
            a.setTitle("Cancel")
            a.setMessage("Are you sure to delete this request")
            a.setPositiveButton("Yes",{DialogInterface, i ->

                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(auth.currentUser!!.uid.toString()).child(getRef(position).key.toString()).removeValue()
            })

            a.setNegativeButton("No",{DialogInterface, i ->


            })
            a.show()
        }



    }




    class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview){


       lateinit var auth2 : FirebaseAuth
        val name : TextView = itemview.findViewById(R.id.txtname) as TextView
        val email : TextView = itemview.findViewById(R.id.txtemail) as TextView
        val phoneno : TextView = itemview.findViewById(R.id.txtphone) as TextView
        val timing : TextView = itemview.findViewById(R.id.txttiming)  as TextView
        val Datetext : TextView = itemview.findViewById(R.id.txtdate) as TextView
        val accept : Button = itemview.findViewById(R.id.btnaccept) as Button
        val cancel : ImageView = itemview.findViewById(R.id.btncancel) as ImageView
        val cancelbutton : Button = itemview.findViewById(R.id.buttoncancel) as Button
        val hid : TextView = itemview.findViewById(R.id.txthid) as TextView
        val ageview : TextView = itemview.findViewById(R.id.txtage)

        fun AddtoMypatients(){

            auth2 = FirebaseAuth.getInstance()


        }

        //function to send data to activeappointment node of user

        fun createactiveappointment(){


            var items = HashMap<String,Any>()
            items.put("With",name.text.toString())
            items.put("date",Datetext)
            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ActiveAppointments").setValue(items).addOnFailureListener{


            }
        }


    }








}