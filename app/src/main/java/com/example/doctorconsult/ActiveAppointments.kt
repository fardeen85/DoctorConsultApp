package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_active_appointments.*
import kotlinx.android.synthetic.main.activity_phistory.*

class ActiveAppointments : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var adapter : ActiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_appointments)

        auth = FirebaseAuth.getInstance()

        val options = FirebaseRecyclerOptions.Builder<ActiveModel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ActiveAppointments").child(auth.currentUser!!.uid.toString()), ActiveModel::class.java)
            .build()


        appointmentrecycle.layoutManager = LinearLayoutManager(this)
        adapter = ActiveAdapter(this,options)
        appointmentrecycle.adapter = adapter
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
