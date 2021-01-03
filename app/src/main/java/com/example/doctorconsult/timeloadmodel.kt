package com.example.doctorconsult

class timeloadmodel{

    var Time1 : String? = null
    var Time2 : String? = null
    var Time3 : String? = null
    var Time4 : String? = null
    var Time5 : String? = null

    constructor() // firebase
    constructor(t1 : String, t2 : String, t3 : String, t4 : String, t5 : String){

        this.Time1 = t1
        this.Time2 = t2
        this.Time3 = t3
        this.Time4 = t4
        this.Time5 = t5
    }
}