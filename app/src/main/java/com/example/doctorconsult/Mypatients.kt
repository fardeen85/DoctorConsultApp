package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_appointment_requests.*
import kotlinx.android.synthetic.main.activity_doctor_dash.*
import kotlinx.android.synthetic.main.activity_mypatients.*

class Mypatients : AppCompatActivity() {

    lateinit var adapter : PatientsAdapter
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypatients)

        var i : Bundle? = intent.extras
        var dname : Any? = i!!.get("Dname").toString()
        auth = FirebaseAuth.getInstance()

        val options = FirebaseRecyclerOptions.Builder<PatientModel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Mypatients").child(auth.currentUser!!.uid.toString()), PatientModel::class.java)
            .build()


        patientrecycler.layoutManager = LinearLayoutManager(this)
        adapter = PatientsAdapter(options,this,dname.toString())
        patientrecycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
