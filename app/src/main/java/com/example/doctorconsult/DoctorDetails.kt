package com.example.doctorconsult

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_doctor_details.*

class DoctorDetails : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)



        //set toolbar as actionbar of collapsing toolbar layout
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null)
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)


        //get id passed from previouse activity
        var b : Bundle? = intent.extras
        var id : Any? = b!!.get("id")

        //load data from firebase to our views in layout

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(id.toString()).addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    txtshowdocname.setText(snapshot.child("Name").getValue().toString())
                    txtdisplayexperience.setText(snapshot.child("Experience").getValue().toString())
                    txtdisplayspecialty.setText(snapshot.child("Feild").getValue().toString())
                    txtdisplaygender.setText(snapshot.child("Gender").getValue().toString())
                    txtdisplaydegree.setText(snapshot.child("CollageDegree").getValue().toString())
                    txtdisplayphone.setText(snapshot.child("Cell").getValue().toString())
                    Glide.with(profileimage.context).load(snapshot.child("Image").getValue().toString()).into(profileimage)
                    collapsetoolbar.setTitle(snapshot.child("Name").getValue().toString())
                }
            }


        })

        //fab button startActivity for select doctor timings

        var fabbook = findViewById<FloatingActionButton>(R.id.appointbook)


        fabbook.setOnClickListener {



                var intent = Intent(this, Selecttiming::class.java)
                intent.putExtra("id", id.toString())
                intent.putExtra("name", txtshowdocname.text.toString())
                startActivity(intent)

        }

        //call icon listner to take user direct to dialer and passe the given number in dialer

        call.setOnClickListener {


            var i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(txtdisplayphone.text.toString())))
            startActivity(i)

        }





    }
}
