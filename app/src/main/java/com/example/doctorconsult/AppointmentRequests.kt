package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_appointment_requests.*

class AppointmentRequests : AppCompatActivity() {

    lateinit var adapter : FirebaseAdapter
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_requests)

        val i:Bundle? = intent.extras
        var name : Any? = i!!.get("Name")
        auth = FirebaseAuth.getInstance()

        textView4.setText(name.toString())

        val options = FirebaseRecyclerOptions.Builder<Model>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(auth.currentUser!!.uid.toString()), Model::class.java)
            .build()


        recycler.layoutManager = LinearLayoutManager(this)
        adapter = FirebaseAdapter(options,this,name.toString())
        recycler.adapter = adapter


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
