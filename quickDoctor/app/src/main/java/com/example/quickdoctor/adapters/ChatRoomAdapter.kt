package com.example.quickdoctor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickdoctor.R
import com.example.quickdoctor.data.Message

class ChatRoomAdapter( val chatList : ArrayList<Message>): RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {
    val CHAT_MINE = 0
    val CHAT_PARTNER = 1
    val USER_JOIN = 2
    val USER_LEAVE = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view : View? = null
        when(viewType){

            0 ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.row_chat_user,parent,false)
            }

            1 ->
            {
                view = LayoutInflater.from(parent.context).inflate(R.layout.row_chat_partner,parent,false)
            }
        }

        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return chatList[position].viewType
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageData  = chatList[position]
        val userName = messageData.userName;
        val content = messageData.messageContent;
        val viewType = messageData.viewType;

        when(viewType) {

            CHAT_MINE -> {
                holder.message.setText(content)
            }
            CHAT_PARTNER ->{
                holder.userName.setText(userName)
                holder.message.setText(content)
            }
            USER_JOIN -> {
                val text = "${userName} has entered the room"
                holder.text.setText(text)
            }
            USER_LEAVE -> {
                val text = "${userName} has leaved the room"
                holder.text.setText(text)
            }
        }

    }
    inner class ViewHolder(itemView : View):  RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById<TextView>(R.id.username)
        val message = itemView.findViewById<TextView>(R.id.message)
        val text = itemView.findViewById<TextView>(R.id.text)
    }
}