package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_patient_signin.*
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signin.view.*

class Signin : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)




        progressBar.visibility = View.GONE

        auth = FirebaseAuth.getInstance()

        createnew.setOnClickListener {

            startActivity(Intent(this,Register::class.java))
        }

        signinbutton.setOnClickListener {

            if (signinemail.length() == 0 ){

                YoYo.with(Techniques.Shake).duration(900).playOn(signinemail)
                Toast.makeText(this,"Email is empty ",Toast.LENGTH_SHORT).show()
            }

            else if(signinpass.length() == 0 ){

                YoYo.with(Techniques.Shake).duration(900).playOn(passlayout2)
                Toast.makeText(this,"Password is empty ",Toast.LENGTH_SHORT).show()
            }

            else{

                Signin()

            }




        } // end of onclick

        txtdoctorforgotpass.setOnClickListener {

            if (signinemail.text.toString().length == 0){

                YoYo.with(Techniques.Shake).duration(900).playOn(signinemail)
                Toast.makeText(this,"Email feild is empty ",Toast.LENGTH_SHORT).show()

            }

            else{

                val a= AlertDialog.Builder(this)
                a.setTitle("Send Reset Email")
                a.setMessage("reset email will be send to this email address ${signinemail.text.toString()}")
                a.setPositiveButton("Send Email",{DialogInterface, i ->

                    resetpassword(signinemail.text.toString())
                })

                a.setNegativeButton("cancel",{DialogInterface, i ->

                    a.setCancelable(true)
                })

                a.show()


            }


        }



    }

    fun Signin(){


        auth.signInWithEmailAndPassword(signinemail.text.toString(), passlayout2.signinpass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    progressBar.visibility = View.VISIBLE
                    val user = auth.currentUser
                    Handler().postDelayed({progressBar.visibility = View.INVISIBLE},7000)
                    verifydoctorlogin()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this,"${task.exception!!.message}",Toast.LENGTH_SHORT).show()
                    // ...
                }

                // ...
            }
    }

    fun resetpassword( email : String){

        auth.sendPasswordResetEmail(email)
    }


    //this function will check if the login email is available in the firebase realtime database node of that doctor who is logging in if not nothing will happen and if yes doctor will
    //be logged in to prevent conflict so that doctor cant acces patients dashboard if he know passwrd and email and patient cant acces doctors dashboard


    fun verifydoctorlogin()  {



        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(auth.currentUser!!.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){



                    if (snapshot.child("Email").getValue().toString() == auth.currentUser!!.email){

                        startActivity(Intent(this@Signin,DoctorDash::class.java))
                        finish()
                    }
                    else{
                       /* toast("Account does not exist")
                        progressBar.visibility = View.INVISIBLE*/
                    }
                }

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()


    }

    fun toast(msg : String){

        Toast.makeText(this,"$msg",Toast.LENGTH_SHORT).show()
    }
}
