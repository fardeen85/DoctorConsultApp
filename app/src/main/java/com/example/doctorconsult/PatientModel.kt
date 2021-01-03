package com.example.doctorconsult

import android.widget.ImageView
import java.net.URL

class PatientModel {

    var Names : String? = null
    var Email : String? = null
    var Phone_no : String? = null
    var profile : String? = null
    var hid : String? = null
    var Age : String? = null

    constructor() // Firebase


    constructor(pname : String, pemail : String, pno : String, prof : String, hideid : String, age : String){

        this.Names = pname
        this.Email = pemail
        this.Phone_no = pno
        this.profile = prof
        this.hid = hideid
        this.Age = age
    }

}