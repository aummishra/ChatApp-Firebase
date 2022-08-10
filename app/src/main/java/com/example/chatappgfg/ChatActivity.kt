package com.example.chatappgfg

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView : RecyclerView
    private lateinit var messageBox :EditText
    private lateinit var sendButton : ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    //creating a reference of the database to use it
    private lateinit var mDbRef : DatabaseReference



    //Using senderRoom and Receiver room we create a unique room for the pair of sender
    //and receiver so that the message is PRIVATE and is not reflected in all the users.

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        //Receiving the intent passed by UserAdapter.kt
        //we dont need a new intent while receiving, we are using intent from
        // another activity
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid


        mDbRef = FirebaseDatabase.getInstance().getReference()


        //SenderRoom and Receiver Room will create an unqiue room for the
        //sender and the receiver, thats why we have them as follows
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
       //Setting up the name in the chatActivity tool bar

        supportActionBar?.title = name


         chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        //setting-up layout manager to the recycler view and message adapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //the next step is to show data on the recycler view from our database
        //here is the logic for adding data to recycler view
        //.child("Messages) will get us inside the messages
        mDbRef.child("Chats").child(senderRoom!!).child("Messages")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    //clearing the previous values so they dont repeat themselves
                    messageList.clear()

                //Now we will use a for-loop to access all the messages of this
                //Snapshot as this Snapshot contains messages so we will create message
                    // using postSnapshot
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        //so we have the message now we just need to add it into the
                        //message list
                        messageList.add(message!!)
                    }
                    //Since we have added some messages to the messageList so we need to
                    //notify this to the adapter as well.
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        //adding the message to the database
        sendButton.setOnClickListener {
           //main logic part
            //we need to send the message to the database and from the database
            //the message will be received to different user.

            //first we will get what is written in the message
            val message = messageBox.text.toString()
            //Now creating a "Message" object using this message
            val messageObject = Message(message,senderUid) //as Message takes 2 parameters

            //Now we have to set it over the database
            //before that we need to create on more node of "Chats" in our Database
            //i.e, firebase Database

            //What will this push() do?
            //It will create a unique node every time when this push is called

            mDbRef.child("Chats").child(senderRoom!!).child("Messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("Chats").child(receiverRoom!!).child("Messages").push()
                        .setValue(messageObject)
                }

            //so now we have updated the senderRoom and at the same time we have to update the
            //receiver room, because when we send message to someone, your UI as well as the
            //Sender's UI is also updated, so we will add Successlistener and repeat the same thing
            //with receiver room as well

            messageBox.setText("") //this is done because after sending a text and making changes
            // we need our messageBox empty again

            //Now the next step is to show data on the recycler view from our database
            //It is done above


        }
        


    }
}