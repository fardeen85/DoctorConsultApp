package com.example.doctorconsult

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_doctor_dash.*
import kotlinx.android.synthetic.main.activity_patient_dash.*

class DoctorDash : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var drawerlayout : DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_dash)
        setSupportActionBar(toolbar3)
        name.visibility = View.INVISIBLE

        auth = FirebaseAuth.getInstance()

        //progress bar on start of activity
        var pg = ProgressDialog(this)
        pg.setMessage("Please wait....")
        pg.show()
        pg.setCancelable(false)
        /*val status = HashMap<String,Any>()
        status.put("status","empty")
        FirebaseDatabase.getInstance().getReference("ConsultApp").child(auth.currentUser!!.uid.toString()).setValue(status)*/
        Handler().postDelayed({pg.dismiss()},3000)

        //NAVIGATION DRAWER

        drawerlayout = findViewById(R.id.doctordrawer) as DrawerLayout
        toggle = ActionBarDrawerToggle(this,drawerlayout,R.string.Open,R.string.Close)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val navigationview : NavigationView = findViewById(R.id.docnav)
        navigationview.setNavigationItemSelectedListener{menuitem ->

            when(menuitem.itemId){


                R.id.closedoc -> {

                    drawerlayout.closeDrawers()
                    true
                }
                R.id.aboutdoc ->{

                    startActivity(Intent(this,About::class.java))
                    true
                }


                R.id.RateAppdoc ->{

                    val intent = Intent(this,RateApp::class.java)
                    intent.putExtra("Docname",name.text.toString())
                    startActivity(intent)
                    true
                }

                R.id.editprofiledoc ->{

                    startActivity(Intent(this,Updatedoctorprofile::class.java))
                    true
                }
                R.id.logoutdoc -> {

                    auth.signOut()

                    var pg = ProgressDialog(this)
                    pg.setMessage("Logging out....")
                    pg.show()
                    Handler().postDelayed({pg.dismiss()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    },3000)
                    true
                }
                else ->{
                    false
                }


            }

        }

        //navigationdrawer header

        var getheader = navigationview.getHeaderView(0)
        val profile_doc = getheader.findViewById<ImageView>(R.id.doc_profile)
        val nameview = getheader.findViewById<TextView>(R.id.Doc_name)

        var id = auth.currentUser!!.uid

        //load image and name to navigation drawer

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(id).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    nameview.setText(snapshot.child("Name").getValue().toString())
                    name.setText(snapshot.child("Name").getValue().toString())
                    Glide.with(profile_doc.context).load(snapshot.child("Image").getValue().toString()).into(profile_doc)

                }
            }


        })

        //Load number of childs in appointments node to badge

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Appointments").child(id.toString()).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                badgep.setText(snapshot.childrenCount.toString())
            }


        })





        auth = FirebaseAuth.getInstance()




        //startActivity appointmentsrequest

        appoitments.setOnClickListener {

            var i = Intent(this,AppointmentRequests::class.java)
            i.putExtra("Name",name.text.toString())
            startActivity(i)
        }

        //startActivity Mypatients

        mypatient.setOnClickListener {

            var i = Intent(this,Mypatients::class.java)
            i.putExtra("Dname",name.text.toString())
            startActivity(i)
        }

        //startActivity update profile

        updateprofile.setOnClickListener {

            startActivity(Intent(this,Updatedoctorprofile::class.java))
        }

    }

    //backpress not available

    override fun onBackPressed() {

        Toast.makeText(this,"Try Logging out",Toast.LENGTH_SHORT).show()
    }


    override fun onStart() {
        super.onStart()

    }


   //for navigation drawer

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
