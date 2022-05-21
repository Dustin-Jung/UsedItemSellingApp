package com.android.aop.part2.useditemsellingapp.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.aop.part2.useditemsellingapp.DBKey.Companion.CHILD_CHAT
import com.android.aop.part2.useditemsellingapp.DBKey.Companion.DB_USERS
import com.android.aop.part2.useditemsellingapp.R
import com.android.aop.part2.useditemsellingapp.chatdetail.ChatRoomActivity
import com.android.aop.part2.useditemsellingapp.databinding.FragmentChatlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment: Fragment(R.layout.fragment_chatlist) {

    private var binding: FragmentChatlistBinding? = null
    private lateinit var chatListRecyclerViewAdapter: ChatListRecyclerViewAdapter
    private val chatRoomList = mutableListOf<ChatListItem>()

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentChatListBinding = FragmentChatlistBinding.bind(view)
        binding = fragmentChatListBinding


        chatListRecyclerViewAdapter = ChatListRecyclerViewAdapter(onItemClicked = { chatRoom ->
            // 채팅방으로 이동 하는 코드
            context?.let {
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey", chatRoom.key)
                startActivity(intent)
            }
        })

        chatRoomList.clear()

        fragmentChatListBinding.chatListRecyclerView.adapter = chatListRecyclerViewAdapter
        fragmentChatListBinding.chatListRecyclerView.layoutManager = LinearLayoutManager(context)


        if (auth.currentUser == null) {
            return
        }

        val chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)

        chatDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatListItem::class.java)
                    model ?: return

                    chatRoomList.add(model)
                }

                chatListRecyclerViewAdapter.submitList(chatRoomList)
                chatListRecyclerViewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


    }


    override fun onResume() {
        super.onResume()

        chatListRecyclerViewAdapter.notifyDataSetChanged()
    }
}