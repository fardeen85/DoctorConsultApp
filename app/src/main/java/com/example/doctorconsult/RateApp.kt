package com.example.doctorconsult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_rate_app.*
import java.util.*
import kotlin.collections.HashMap

class RateApp : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_app)

        val i : Bundle? = intent.extras
        val Docname : String? = i!!.getString("Docname")
        txtdocname.setText(Docname.toString())


        auth = FirebaseAuth.getInstance()

        //to send rating and review of user
        buttonsubmitrating.setOnClickListener {

            sendratingandreview()
        }


    }

    fun sendratingandreview(){

        val reviewdata = HashMap<String,Any>()
        reviewdata.put("Name",txtdocname.text.toString())
        reviewdata.put("Rating",ratingBar.rating.toString())
        reviewdata.put("Review",txtreview.text.toString())
        val key = UUID.randomUUID().toString()
        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Ratings").child(auth.currentUser!!.uid.toString()).child(key).setValue(reviewdata).addOnCompleteListener{

            Toast.makeText(this,"Thank you for your cooperation",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
