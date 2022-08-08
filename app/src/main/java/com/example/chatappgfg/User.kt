package com.example.chatappgfg

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    constructor(){

    }
//for firebase to parse data we need a constructor inside a class
    //Q} why String?
    //Ans} Because
    constructor(name:String?, email:String?, uid:String?){
        this.name = name
        this.email = email
        this.uid = uid
    }


}