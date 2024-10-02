package org.benedetto.catsapp

internal class Person (var name: String = "NoName", var age: Int = 0, var city: String = "Unknown" ) {

    fun moveTo(_city:String){
        city = _city
    }

    fun incrementAge(){
        age++
    }



}