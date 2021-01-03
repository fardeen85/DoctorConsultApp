package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_timting.*
import kotlin.collections.HashMap

class Add_timting : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timting)

        auth = FirebaseAuth.getInstance()

        timingadded.setOnClickListener {


            if (t1.length() == 0 && t2.length() == 0){

                Toast.makeText(this,"Add atleast two timings",Toast.LENGTH_SHORT).show()


            }

            else{
                var randonkey = FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctimings").push().key
                var timemap  = HashMap<String,Any>()
                timemap.put("Time1",t1.text.toString()+spinner1.selectedItem.toString())
                timemap.put("Time2",t2.text.toString()+spinner2.selectedItem.toString())
                timemap.put("Time3",t3.text.toString()+spinner3.selectedItem.toString())
                timemap.put("Time4",t4.text.toString()+spinner4.selectedItem.toString())
                timemap.put("Time5",t5.text.toString()+spinner5.selectedItem.toString())

                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctimings").child(auth.currentUser!!.uid.toString()).child(randonkey.toString()).setValue(timemap).addOnSuccessListener {

                    Toast.makeText(this,"Timing added",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Signin::class.java))
                    finish()

                }.addOnFailureListener{

                    Toast.makeText(this,"Timing adding failed",Toast.LENGTH_SHORT).show()

                }
            }

        }


    }

    override fun onBackPressed() {
        Toast.makeText(this,"You cant go back..proceed further please!!",Toast.LENGTH_SHORT).show()
    }
}
