package com.android.aop.part2.useditemsellingapp.chatdetail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.useditemsellingapp.DBKey.Companion.DB_CHATS
import com.android.aop.part2.useditemsellingapp.R
import com.android.aop.part2.useditemsellingapp.databinding.ActivityChatroomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatRoomActivity : AppCompatActivity(){

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val chatList = mutableListOf<ChatItem>()
    private val chatItemRecyclerViewAdapter = ChatItemRecyclerViewAdapter()
    private var chatDB: DatabaseReference? = null

    private val binding by lazy { ActivityChatroomBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val chatKey = intent.getLongExtra("chatKey", -1)

        chatDB = Firebase.database.reference.child(DB_CHATS).child("$chatKey")

        chatDB?.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatItem::class.java)
                chatItem ?: return

                chatList.add(chatItem)
                chatItemRecyclerViewAdapter.addAll(chatList)
                chatItemRecyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}


        })

        binding.chatRecyclerView.adapter = chatItemRecyclerViewAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.sendButton.setOnClickListener {
            val chatItem = auth.currentUser?.let { it1 ->
                ChatItem(
                    senderId = it1.uid,
                    message = binding.messageEditText.text.toString()
                )
            }

            chatDB?.push()?.setValue(chatItem)

        }


    }
}