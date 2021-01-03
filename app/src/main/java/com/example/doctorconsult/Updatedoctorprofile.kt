package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_updatedoctorprofile.*

class Updatedoctorprofile : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatedoctorprofile)


        hif.visibility = View.INVISIBLE

        auth = FirebaseAuth.getInstance()

        //load previuose data from firebase to views

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                hif.setText(snapshot.child("Feild").getValue().toString())
                nameupdate.setText(snapshot.child("Name").getValue().toString())
                experieneupdate.setText(snapshot.child("Experience").getValue().toString())
                degreeupdate.setText(snapshot.child("CollageDegree").getValue().toString())
                phoneupdate.setText(snapshot.child("Cell").getValue().toString())
                genderupdate.setText(snapshot.child("Gender").getValue().toString())

            }


        })

        //update name in Doctors and viewdoctors node

        btnnameupdate.setOnClickListener {

            val namemap = HashMap<String,Any>()
            namemap.put("Name","Dr."+nameupdate.text.toString())

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnCompleteListener{

                Toast.makeText(this,"Name updated",Toast.LENGTH_SHORT)
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(hif.text.toString()).child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnFailureListener{

                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT)
                }
            }


        }
        //update experience in Doctors and viewdoctors node

        btnexperienceupdate.setOnClickListener {

            val namemap = HashMap<String,Any>()
            namemap.put("Experience",experieneupdate.text.toString())

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnCompleteListener{

                Toast.makeText(this,"Experience updated",Toast.LENGTH_SHORT)
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(hif.text.toString()).child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnFailureListener{

                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT)
                }
            }

        }

        //update collage/degree in Doctors and viewdoctors node

        btndegreeupdate.setOnClickListener {

            val namemap = HashMap<String,Any>()
            namemap.put("CollageDegree",degreeupdate.text.toString())

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnCompleteListener{

                Toast.makeText(this,"Collage/Degree updated",Toast.LENGTH_SHORT)
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(hif.text.toString()).child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnFailureListener{

                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT)
                }
            }
        }

        //update phone in Doctors and viewdoctors node

        btnphoneupdate.setOnClickListener {

            val namemap = HashMap<String,Any>()
            namemap.put("Cell",phoneupdate.text.toString())

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnCompleteListener{

                Toast.makeText(this,"Phone nuber updated",Toast.LENGTH_SHORT)
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(hif.text.toString()).child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnFailureListener{

                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT)
                }
            }

        }
        //update gender in Doctors and viewdoctors node


        btngenderupdate.setOnClickListener {

            val namemap = HashMap<String,Any>()
            namemap.put("Gender",genderspinner.selectedItem.toString())

            FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnCompleteListener{

                Toast.makeText(this,"Gender updated",Toast.LENGTH_SHORT)
                FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(hif.text.toString()).child(auth.currentUser!!.uid.toString()).updateChildren(namemap).addOnFailureListener{

                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT)
                }
            }

        }


    }



}
