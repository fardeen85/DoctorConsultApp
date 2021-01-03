package com.example.doctorconsult

class Prescriptionmodel {

    var name : String? = null
    var age : String? = null
    var date : String? = null
    var prescription : String? = null
    var Dname : String? = null

    constructor() // firebase
    constructor( Name : String, age : String, prescript : String, dname : String, Date : String){

        this.name = Name
        this.age = age
        this.prescription = prescript
        this.date = Date
        this.Dname = dname

    }

}