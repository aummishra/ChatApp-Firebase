package com.example.chatappgfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userList = ArrayList()
        adapter = UserAdapter(this,userList)






    }
    //java.lang.RuntimeException: Unable to start activity ComponentInfo{com.example.chatappgfg/com.example.chatappgfg.MainActivity}: android.view.InflateException: Binary XML file line #7: Binary XML file line #7: Error inflating class android.support.constraint.ConstraintLayout
}