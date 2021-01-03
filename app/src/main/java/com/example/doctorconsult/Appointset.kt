package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_appointset.*
import kotlinx.android.synthetic.main.activity_patient_dash.*
import java.util.*
import kotlin.collections.HashMap

class Appointset : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var sname : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointset)

        auth = FirebaseAuth.getInstance()

        //getting name id and time from previouse activity

        var i : Bundle? = intent.extras
        var id : Any? = i!!.get("id")
        var time : Any? = i.get("time")
        var date : Any? = i.get("date")
        var name : Any? = i.get("name")
        sname = name.toString()

        emailsub.setText(auth.currentUser!!.email.toString())



        display.setText("Confirm your appointment at $time on $date with $name")

        buttonsubmit.setOnClickListener {

            var puts = HashMap<String,Any>()
            puts.put("Name",namesubmit.text.toString())
            puts.put("Email",emailsub.text.toString())
            puts.put("phone",phonesub.text.toString())
            puts.put("Time",time.toString())
            puts.put("Date",date.toString())
            puts.put("status","not accepted")
            puts.put("Age",agesub.text.toString())
            //this id will be send to doctor and will be invisible to doctor just used to make app send prescription  to requested patient
            puts.put("hid",auth.currentUser!!.uid.toString())

            val key = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").push().key


            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(id.toString()).child(key.toString()).setValue(puts).addOnCompleteListener{

                Toast.makeText(this,"Request submitted",Toast.LENGTH_SHORT).show()
                History()
                startActivity(Intent(this,PatientDash::class.java))
                finish()

            }.addOnFailureListener{

                Toast.makeText(this,"Request failed",Toast.LENGTH_SHORT).show()
            }
        }


    }

    //sent data to history node of the requesting user

    fun History(){

        var value = HashMap<String,Any>()
        value.put("dname",sname.toString())
        value.put("status","sent")

        var key = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("PatientHistoreis").push().key.toString()
        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("PatientHistoreis").child(auth.currentUser!!.uid.toString()).child(key).setValue(value)
    }
}
