package com.example.chatappgfg

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class UserAdapter(val context: Context,val userList : ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
       val textName =  itemView.findViewById<TextView>(R.id.txt_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
         val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name

        //setting up on click listener on the recycle view items so that
        //it can open chat activity

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            //since we are not inside an activity so we cant start activity by
            //using startActivity(), so we will use
            //passing some values so that we can use it to show on the toolbar of chat
                //activity
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
           //FirebaseAuth.getInstance().currentUser?.uid -> uid of logged in user
            //currentUser.uid -> uid of current user that we are going to text

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}