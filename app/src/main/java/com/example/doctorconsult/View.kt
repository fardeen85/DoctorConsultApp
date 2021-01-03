package com.example.doctorconsult

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_doctor_details.*
import kotlinx.android.synthetic.main.activity_view.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.View
import com.annimon.stream.operator.IntArray



class View : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)



    }


    //function checks internet if yes returns true and if not returns false

    fun isconnnected(login : Context) : Boolean{


        val connectionmanager : ConnectivityManager = login.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wificon : NetworkInfo = connectionmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobilecon : NetworkInfo = connectionmanager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wificon !=null && wificon.isConnected || mobilecon != null && mobilecon.isConnected ){

            return true

        }
        else{

            return  false

        }

    }

    fun showdialgue(){

        var a = AlertDialog.Builder(this)
        a.setTitle("No Internet")
        a.setIcon(android.R.drawable.ic_dialog_alert)
        a.setMessage("No internet connection")
        a.setPositiveButton("Connect",{DialogInterface, i ->

            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        })

        a.setNegativeButton("Cancel",{DialogInterface, i ->

           finishAffinity()

        })

        a.show()
    }

    override fun onStart() {
        super.onStart()

        //in on start if isconnected return true it will start MainActivity and if not it will
        //show dialgue to connect the internet
        Handler().postDelayed({

            if (isconnnected(this)){

                startActivity(Intent(this,MainActivity::class.java))

            }

            else{

                showdialgue()
                check.setText("No Internet")

            }
        },1600)
    }
}
