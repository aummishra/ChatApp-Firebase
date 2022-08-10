package com.example.chatappgfg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter : UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef =  FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        //we can not access the uids because they are unique so,
        // we will access all the elements of" user" one by one
        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            //snapshot will be used to get data from the database
            //it is a snap shot of how the schema of the database is
            //so we will traverse through the childs of this snapshot
            //using for loop
            override fun onDataChange(snapshot: DataSnapshot) {
                //before we run this for loop we need to clear the userList
                //because this onDataChange method is called everytime when we
                //have a change in data. so whenever a new user is added then that forloop
                //will run again and the same previous values will get repeated again
                //so therefore we need to clear old list so,
                userList.clear()

                for(postSnapshot in snapshot.children){
                    //so when we enter inside a node
                    // then we have to create a userobject and add it into our list
                    //inside get value we will pass what kind of value we will need
                        //and we want a user so we passed "User::class.java"
                        val currentUser = postSnapshot.getValue(User::class.java)
                    //then adding this user to our userlist
                    //To not show the chat option of the logged in user
                    //we need to check if the uid of current user is equal to
                    //uid of logged in user or not
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                //also we have to notify this to our adapter
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

   /* override fun onOptionsMenuClosed(menu: Menu?) {
        menuInflater.inflate(R.menu.menu,menu)
        super.onOptionsMenuClosed(menu)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            //write the logic for logout
            mAuth.signOut()
            val intent = Intent(this@MainActivity,LogIn::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }






}