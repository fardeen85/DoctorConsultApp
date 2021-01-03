package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_phistory.*

class Phistory : AppCompatActivity() {

    lateinit var  adapter : historyadapter
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phistory)

        auth = FirebaseAuth.getInstance()

        val options = FirebaseRecyclerOptions.Builder<Phistorymodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("PatientHistoreis").child(auth.currentUser!!.uid.toString()), Phistorymodel::class.java)
            .build()


        precycler.layoutManager = LinearLayoutManager(this)
        adapter = historyadapter(this,options)
        precycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

