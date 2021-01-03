package com.example.doctorconsult

class ActiveModel {

    var With : String? = null
    var date : String? = null
    var time : String? = null

    constructor()

    constructor(withdoc : String, Date : String, timing : String){

        this.With = withdoc
        this.date = Date
        this.time = timing
    }


}