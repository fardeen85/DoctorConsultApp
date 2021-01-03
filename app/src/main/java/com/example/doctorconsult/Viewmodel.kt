package com.example.doctorconsult

class Viewmodel {

    var Name : String? = null
    var Image : String? = null
    var CollageDegree : String? = null

    constructor() // firebase

    constructor(name : String, imgs : String, dc : String){

        this.Name = name
        this.Image = imgs

    }
}