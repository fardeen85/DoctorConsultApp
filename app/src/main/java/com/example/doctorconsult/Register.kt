package com.example.doctorconsult

import android.app.AuthenticationRequiredException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_patient_signin.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.activity_signin.*

class Register : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        registerbutton.setOnClickListener {

            if(txtemail.text.toString().length == 0){

                Toast.makeText(this,"Email is empty",Toast.LENGTH_SHORT).show()
                YoYo.with(Techniques.Shake).duration(900).playOn(txtemail)
            }
            else if(txtpass.text.toString().length == 0){

                Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT).show()
                YoYo.with(Techniques.Shake).duration(900).playOn(txtpass)
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


        auth.createUserWithEmailAndPassword(txtemail.text.toString(), passlayout.txtpass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this,DoctorInfo::class.java))
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
