package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_prescriptions.*

class Prescriptions : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var adapter : PrescriptionRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescriptions)

        val i:Bundle? = intent.extras
        var dname : Any? = i!!.get("Dname")

        auth = FirebaseAuth.getInstance()

        val options = FirebaseRecyclerOptions.Builder<Prescriptionmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Prescriptions").child(auth.currentUser!!.uid.toString()), Prescriptionmodel::class.java)
            .build()

        prescreycler.layoutManager = LinearLayoutManager(this)
        adapter = PrescriptionRecyclerAdapter(this,options,dname.toString())
        prescreycler.adapter =  adapter
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
