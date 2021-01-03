package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_doctor_info.*
import kotlinx.android.synthetic.main.activity_pateint_info.*

class PateintInfo : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pateint_info)

        auth = FirebaseAuth.getInstance()

        uid = auth.currentUser!!.uid

        txtpemail.setText(auth!!.currentUser!!.email.toString())

        createpatient.setOnClickListener {

            Addpatientdata()
            startActivity(Intent(this,PatientSignin::class.java))
        }

        createpatient.setOnClickListener {

            Addpatientdata()

        }

    }

    //function for creating patients node

    fun Addpatientdata(){


        var id = rdp.checkedRadioButtonId
        var rd = findViewById<RadioButton>(id)


        var values = HashMap<String,Any>()
        values.put("Name",txtpname.text.toString())
        values.put("Email",auth.currentUser!!.email.toString())
        values.put("Age",txtage.text.toString())
        values.put("Gender",rd.text.toString())

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Patients").child(uid.toString()).setValue(values).addOnCompleteListener{


            startActivity(Intent(this,PatientSignin::class.java))
            finish()

        }

    }



    override fun onBackPressed() {

        Toast.makeText(this,"Please proceed further",Toast.LENGTH_SHORT).show()

    }

}


