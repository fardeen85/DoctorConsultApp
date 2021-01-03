package com.example.doctorconsult

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_doctor_dash.*
import kotlinx.android.synthetic.main.activity_patient_dash.*
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.view.*

class PatientDash : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var drawerlayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_dash)
        setSupportActionBar(toolbar2)




        //NavigationDrawer

        drawerlayout = findViewById(R.id.mydrawer) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.Open, R.string.Close)
        drawerlayout.addDrawerListener(toggle)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //NavigationDrawer





        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser!!.uid

        var email = auth.currentUser!!.email



        //startActivity setappointment
        setappointp.setOnClickListener {


            startActivity(Intent(this, View_Doctors::class.java))

        }

        //startActivity viewdoctors

        viewdoctors.setOnClickListener {

            startActivity(Intent(this, ActiveAppointments::class.java))

        }

        //startActivity patient history

        phistory.setOnClickListener {

            startActivity(Intent(this, Phistory::class.java))
        }






        //navigation item select listner

        val navigationview = findViewById<NavigationView>(R.id.navview)
        val viewheader = navigationview.getHeaderView(0)
        val outputname = viewheader.findViewById<TextView>(R.id.anonymouse)
        val emailoutput = viewheader.findViewById<TextView>(R.id.emailunknown)
        navigationview.setNavigationItemSelectedListener { menuItem ->


            when (menuItem.itemId) {

                R.id.about -> {
                    startActivity(Intent(this,About::class.java))
                    true
                }
                R.id.logout -> {

                    auth.signOut()
                    var pg = ProgressDialog(this)
                    pg.setMessage("Logging out....")
                   /* val status = HashMap<String,Any>()
                    status.put("status","empty")
                    FirebaseDatabase.getInstance().getReference("ConsultApp").child(auth.currentUser!!.uid.toString()).setValue(status)*/
                    pg.show()
                    Handler().postDelayed({pg.dismiss()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    },3000)
                    true
                }

                R.id.RateApp ->{

                    val intent = Intent(this,RateApp::class.java)
                    intent.putExtra("Docname",outputname.text.toString())
                    startActivity(intent)
                    true
                }
                R.id.close -> {
                    drawerlayout.closeDrawers()
                    true
                }
                else -> {

                    true
                }
            }
        }



        //load from firebase name and email to navigation drawers header

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Patients").child(user).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                outputname.setText(snapshot.child("Name").getValue().toString())
                emailoutput.setText(snapshot.child("Email").getValue().toString())

            }


        })

        //count childs in activeappointments node and load to badge to show number

        FirebaseDatabase.getInstance().getReference(). child("ConsultApp").child("ActiveAppointments").child(user).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                badge.setText(snapshot.childrenCount.toString())

            }


        })

        //startActivity prescriptions

        openprescriptions.setOnClickListener {

            var i = Intent(this,Prescriptions::class.java)
            i.putExtra("Dname",outputname.text.toString())
            startActivity(i)

        }
    }

    //backpress not available
    override fun onBackPressed() {

        Toast.makeText(this,"Try Logging out",Toast.LENGTH_SHORT).show()
    }

    //for navigation drawer


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
