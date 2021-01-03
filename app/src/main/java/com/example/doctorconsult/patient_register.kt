package com.example.doctorconsult

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_patient_register.*
import kotlinx.android.synthetic.main.activity_patient_register.view.*
import kotlinx.android.synthetic.main.activity_patient_signin.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*

class patient_register : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)

        auth = FirebaseAuth.getInstance()

        btnregisterp.setOnClickListener {

            if (patientemail2.text.toString().length == 0){

                Toast.makeText(this,"Email is empty", Toast.LENGTH_SHORT).show()

                YoYo.with(Techniques.Shake).duration(900).playOn(patientemail2)
            }
            else if(txtrpp.text.toString().length == 0){

                YoYo.with(Techniques.Shake).duration(900).playOn(patientemail)
                Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show()
            }
            else{

                SignUp()

            }


        }
    }

    override fun onStart() {
        super.onStart()

    }


    fun SignUp(){


        auth.createUserWithEmailAndPassword(patientemail2.text.toString(), pprl.txtrpp.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this,PateintInfo::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                   Toast.makeText(this,"${task.exception!!.message}",Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }
}
