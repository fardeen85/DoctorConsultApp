package com.example.doctorconsult

import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_doctor_info.*
import kotlinx.android.synthetic.main.activity_doctor_info.txtname
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.customviewappointments.*
import java.util.*
import kotlin.collections.HashMap

class DoctorInfo : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var myref : FirebaseDatabase
    var user : FirebaseUser? = null
    lateinit var imageref: FirebaseStorage
    lateinit var storageref: StorageReference
    private var imguri: Uri? = null
    var id : String? = null
    lateinit var downloadUrl : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_info)

        //Init
        auth = FirebaseAuth.getInstance()
        myref = FirebaseDatabase.getInstance()
        imageref = FirebaseStorage.getInstance()
        storageref = imageref.getReference()

        //register buttton

        btnregister.setOnClickListener {

            if (imguri == null){

                Toast.makeText(this,"Please select a profile picture",Toast.LENGTH_SHORT).show()
            }

            else if (txtname.text.toString().length == 0){

                Toast.makeText(this,"Name cannnot be empty",Toast.LENGTH_SHORT).show()
            }

            else if(txtemailinfo.text.toString().length == 0){

                Toast.makeText(this,"Email cannnot be empty",Toast.LENGTH_SHORT).show()
            }
            else if(txtdegree.text.toString().length == 0){

                Toast.makeText(this,"Degree feild cannnot be empty",Toast.LENGTH_SHORT).show()
            }

            else if(txthphoneinfo.text.toString().length == 0){

                Toast.makeText(this,"Please enter your mobile number",Toast.LENGTH_SHORT).show()
            }

            else{

                addinfo()
                ViewDoctors()
                imageupload()
                finish()

            }



        }

        //calling function to get image in buttons listner

        floatingActionButton.setOnClickListener {

            getimage()
        }




        //get and set email from current user to the editext of email
        user = auth.currentUser
        txtemailinfo.setText(user!!.email)

        if (user == null){

            startActivity(Intent(this,Register::class.java))
        }
        else{




        }
    }


    //adding data to doctors node

    fun addinfo(){

        var id = rdgroup.checkedRadioButtonId
        var radiobtn = findViewById<RadioButton>(id)

        var data = HashMap<String,Any>()
        data.put("Name","Dr "+txtname.text.toString())
        data.put("Email",txtemailinfo.text.toString())
        data.put("Feild",spinnerfeild.selectedItem)
        data.put("Gender",radiobtn.text.toString())
        data.put("Experience",txtexp.text.toString())
        data.put("CollageDegree",txtdegree.text.toString())
        data.put("Cell",txthphoneinfo.text.toString())
        data.put("type","doctor")


        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(user!!.uid).setValue(data).addOnCompleteListener{

            Snackbar.make(thisinfo,"Doctor Data Added",Snackbar.LENGTH_LONG).show()

        }

    }

    //creating viewdoctors node that will be loaded to recycler view for patient to view in patients part

    fun ViewDoctors(){

        var id = rdgroup.checkedRadioButtonId
        var radiobtn = findViewById<RadioButton>(id)

        var data = HashMap<String,Any>()
        data.put("Name","Dr "+" "+txtname.text.toString())
        data.put("Email",txtemailinfo.text.toString())
        data.put("Feild",spinnerfeild.selectedItem)
        data.put("Gender",radiobtn.text.toString())
        data.put("Experience",txtexp.text.toString())
        data.put("CollageDegree",txtdegree.text.toString())
        data.put("Cell",txthphoneinfo.text.toString())

        FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(spinnerfeild.selectedItem.toString()).child(user!!.uid.toString()).setValue(data).addOnFailureListener{

            Snackbar.make(thisinfo,"Failed creating view doctors",Snackbar.LENGTH_LONG).show()
        }
    }

    //profile image upload and and url sent to required child in realtime database

    fun imageupload() {


        var id = UUID.randomUUID().toString()
        val storagerefference = storageref.child("Doctors/$id")

        storagerefference.putFile(imguri!!).addOnSuccessListener {

            //Toast.makeText(this, "Image uploaded successfuly", Toast.LENGTH_SHORT).show()

        }.addOnCompleteListener {


            if (it.isSuccessful) {

                storagerefference.downloadUrl.addOnSuccessListener {

                    var imgmap = HashMap<String,Any>()
                    imgmap.put("Image", it.toString())

                   // FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(user!!.uid).updateChildren(imgmap)
                    FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("ViewDoctors").child(spinnerfeild.selectedItem.toString()).child(user!!.uid.toString()).updateChildren(imgmap)
                    FirebaseDatabase.getInstance().getReference().child("ConsultApp").child("Doctors").child(user!!.uid.toString()).updateChildren(imgmap)
                    startActivity(Intent(this,Add_timting::class.java))


                }



            } else {
                Toast.makeText(this,"${it.exception}", Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener {

            Toast.makeText(this, "Image uploading failed"+it, Toast.LENGTH_SHORT).show()
        }

    }

    //get image to profile imageview


    fun getimage() {

        var i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(i, 1)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            imguri = data.data
            profile_image.setImageURI(imguri)

        }
    }

    override fun onBackPressed() {

        Toast.makeText(this,"Please proceed further",Toast.LENGTH_SHORT).show()
    }




}
