package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_patient_signin.*
import kotlinx.android.synthetic.main.activity_signin.*

class PatientSignin : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_patient_signin)



        //make progressbar invisible

        signprogress.visibility = View.INVISIBLE

        //start Activity to create new account
        createnewp.setOnClickListener {

            startActivity(Intent(this,patient_register::class.java))
        }

        //signin on clicklistner

        signinpatient.setOnClickListener {

            if (patientemail.length() == 0 ){

                YoYo.with(Techniques.Shake).duration(900).playOn(patientemail)
                Toast.makeText(this,"Email is empty ",Toast.LENGTH_SHORT).show()
            }

            else if(txtpatientpass.length() == 0 ){

                YoYo.with(Techniques.Shake).duration(900).playOn(patientpasslayout)
                Toast.makeText(this,"Password is empty ",Toast.LENGTH_SHORT).show()
            }

            else{

                Signinpatient()

            }




        } // end of onclick

        //forgot password listner

        txtpatientforgotpass.setOnClickListener {


            if (patientemail.text.toString().length == 0){

                YoYo.with(Techniques.Shake).duration(900).playOn(patientemail)
                Toast.makeText(this,"Email feild is empty ",Toast.LENGTH_SHORT).show()

            }

            else{

                val a= AlertDialog.Builder(this)
                a.setTitle("Send Reset Email")
                a.setMessage("reset email will be send to this email address ${patientemail.text.toString()}")
                a.setPositiveButton("Send Email",{DialogInterface, i ->

                    resetpassword(patientemail.text.toString())
                })

                a.setNegativeButton("cancel",{DialogInterface, i ->

                    a.setCancelable(true)
                })
                a.show()


            }

        }





    }


    fun resetpassword( email : String){

        auth.sendPasswordResetEmail(email)
    }


    fun Signinpatient(){

        auth.signInWithEmailAndPassword(patientemail.text.toString(),txtpatientpass.text.toString()).addOnCompleteListener{ task ->

            if (task.isSuccessful){

                signprogress.visibility = View.VISIBLE
                Handler().postDelayed({signprogress.visibility = View.INVISIBLE},7000)
                verifypatientlogin()

            }
            else{

                Toast.makeText(this,"${task.exception!!.message}",Toast.LENGTH_SHORT).show()
            }

        }



    }

    //this function will check if the login email is available in the firebase realtime database node of that patient who is loggin in if not nothing will happen and if yes patient will
    //be logged in to prevent conflict so that doctor cant acces patients dashboard if he know passwrd and email and patient cant acces doctors dashboard

    fun verifypatientlogin()  {



        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Patients").child(auth.currentUser!!.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){



                    if (snapshot.child("Email").getValue().toString() == auth.currentUser!!.email){

                        startActivity(Intent(this@PatientSignin,PatientDash::class.java))
                        finish()
                    }
                    else{

                        /*signprogress.visibility = View.INVISIBLE*/
                    }
                }

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


}
