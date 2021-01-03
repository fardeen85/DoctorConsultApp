package com.example.doctorconsult

import android.widget.ImageView

class Model {
    var Email: String? = null
    var Name: String? = null
    var phone: String? = null
    var Time : String? = null
    var Date : String? = null
    var status : String? = null
    var Done : String? =null
    var img : String? = null
    var hid : String? = null
    var Age : String? = null

    constructor() {}  // Needed for Firebase

    constructor(name: String, mail: String, phone: String, time : String, Date : String, state : String, done : String,image : String,hid : String, age : String) {
        this.Name = name
        this.Email = mail
        this.phone = phone
        this.Time = time
        this.Date = Date
        this.status = state
        this.Done = done
        this.img = img
        this.hid = hid
        this.Age = age
    }
}