package com.example.quickdoctor.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickdoctor.R
import com.example.quickdoctor.adapters.ChatRoomAdapter
import com.example.quickdoctor.data.Message
import com.example.quickdoctor.data.MessageType
import com.example.quickdoctor.data.RoomRequest
import com.example.quickdoctor.data.SendMessage
import com.example.quickdoctor.extensions.TAG
import com.example.quickdoctor.extensions.findNavController
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.room.*


class Room: Fragment() {

    lateinit var socket: Socket
    lateinit var chatRoomAdapter: ChatRoomAdapter
    val chatList: ArrayList<Message> = arrayListOf();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.room, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }
    private fun setup() {
        chatRoomAdapter = ChatRoomAdapter(chatList)
        recyclerView.adapter = chatRoomAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


        try {
            socket = IO.socket("http://192.168.43.34:4000")
            Log.v(TAG,"succces")
        }catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }
        socket.connect()
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on("newUsertoChatRoom", onNewUser)
        socket.on("updateChat", onUpdateChat)

        leave.setOnClickListener {
            onDestroy()
        }
        send.setOnClickListener {
            this.sendMessage()
        }
    }

    var onConnect = Emitter.Listener {
        val data = RoomRequest("userName", "roomName")
        val jsonData = Gson().toJson(data)
        socket.emit("enteringRoom", jsonData)

    }

    var onNewUser = Emitter.Listener {
        Log.v(TAG, "on new user")

    }
    var onUpdateChat = Emitter.Listener {
        val chat: Message = Gson().fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        Log.v(TAG,chat.toString())
        addItemToRecyclerView(chat)
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        findNavController().navigate(R.id.homePage)
    }

    private fun sendMessage() {
        val messageContent = editText.text.toString()
        Log.v(TAG,messageContent)

        val sendData = SendMessage("userName", messageContent, "roomName")
        val jsonData = Gson().toJson(sendData)
        socket.emit("newMessage", jsonData)

        val message = Message("userName", messageContent, "roomName", MessageType.CHAT_MINE.index)
        addItemToRecyclerView(message)
    }

    private fun addItemToRecyclerView(message: Message) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        requireActivity().runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            editText.setText("")
            recyclerView.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }
}