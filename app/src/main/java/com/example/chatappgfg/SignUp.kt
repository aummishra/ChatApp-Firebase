package com.example.chatappgfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var etext2 : EditText
    private lateinit var etext1 : EditText
    private lateinit var etext : EditText
    //private lateinit var Sin : Button
    private lateinit var Sup : Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        etext1 = findViewById(R.id.et1)
        etext2 = findViewById(R.id.et2)
        etext = findViewById(R.id.et)
        //Sin = findViewById(R.id.Signin)
        Sup = findViewById(R.id.Signup)
        Sup.setOnClickListener {
            val name = etext2.text.toString()
            val email = etext.text.toString()
            val password = etext1.text.toString()

            signup(name,email,password)
        }
    }
    private fun signup(name: String, email:String, password:String){
        //Logic for logging in user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                   //code for jumping to home activity
                       addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,"Some Error Occurred",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun addUserToDatabase(name:String, email: String,uid: String){
         mDbRef = FirebaseDatabase.getInstance().getReference()
        //Now adding user to the database,
        //we will set a parent path as "user" and then child path will unique as uid then
        // we will store a user's information by using set value and passing name,email and uid
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
        //just like this , it will add an user to the database
    }


}