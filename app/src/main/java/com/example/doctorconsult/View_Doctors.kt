package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_appointment_requests.*
import kotlinx.android.synthetic.main.activity_selecttiming.*
import kotlinx.android.synthetic.main.activity_view__doctors.*

class View_Doctors : AppCompatActivity() {

    lateinit var adapter : ViewAdapter
    lateinit var adapter2 : ViewAdapter
    lateinit var adapter3 : ViewAdapter
    lateinit var adapter4 : ViewAdapter
    lateinit var adapter5: ViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view__doctors)



        //firebaseadapter options to load Dentists in recyclerview

        val options = FirebaseRecyclerOptions.Builder<Viewmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child("DENTIST"), Viewmodel::class.java)
            .build()


        recyclerviewdoctor.layoutManager = LinearLayoutManager(this)
        adapter = ViewAdapter(options,this)


        //firebaseadapter options to load GASTROENETE in recyclerview

        val options2 = FirebaseRecyclerOptions.Builder<Viewmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child("GASTROENETE"), Viewmodel::class.java)
            .build()


        adapter2 = ViewAdapter(options2,this)

        //firebaseadapter options to load GENERAL_PRACTITIONER in recyclerview

        val options3 = FirebaseRecyclerOptions.Builder<Viewmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child("GENERAL_PRACTITIONER"), Viewmodel::class.java)
            .build()

        adapter3 = ViewAdapter(options3,this)


        //firebaseadapter options to load VETERINARY in recyclerview

        val options4 = FirebaseRecyclerOptions.Builder<Viewmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child("VETERINARY"), Viewmodel::class.java)
            .build()

        adapter4 = ViewAdapter(options4,this)


        //firebaseadapter options to load COUNSELLOR in recyclerview

        val options5 = FirebaseRecyclerOptions.Builder<Viewmodel>()
            .setQuery(FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child("COUNSELLOR"), Viewmodel::class.java)
            .build()

        adapter5 = ViewAdapter(options5,this)




        //startadapter for dentist

        chipdentist.setOnClickListener {


                recyclerviewdoctor.adapter = adapter
                adapter.startListening()


        }



        //startadapter for gastroenete

        chipg.setOnClickListener {

                recyclerviewdoctor.adapter = adapter2
                adapter2.startListening()


        }

        //startadapter for general precautioner

        chipgp.setOnClickListener {

            adapter3.startListening()
            recyclerviewdoctor.adapter = adapter3
        }

        //startadapter for general vertinery

        chipv.setOnClickListener {

            adapter4.startListening()
            recyclerviewdoctor.adapter = adapter4
        }

        //startadapter for general counsellor

        chipc.setOnClickListener {

            adapter5.startListening()
            recyclerviewdoctor.adapter = adapter5
        }







    }

    override fun onStart() {
        super.onStart()

    }

    //to stop all adapters

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
        adapter2.stopListening()
        adapter3.stopListening()
        adapter4.stopListening()
        adapter5.stopListening()

    }
}
