package com.example.chatappgfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var etext1 : EditText
    private lateinit var etext : EditText
    private lateinit var Sin : Button
    private lateinit var Sup : Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()//This is how we initialize aur firebase authentication
        etext1 = findViewById(R.id.et1)
        etext = findViewById(R.id.et)
        Sin = findViewById(R.id.Signin)
        Sup = findViewById(R.id.Signup)

        Sup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        Sin.setOnClickListener {
            val email = etext.text.toString()
            val password = etext1.text.toString()

            login(email,password)
        }
    }


    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //code for logging in user
                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this@LogIn,"User Doesn't Exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}