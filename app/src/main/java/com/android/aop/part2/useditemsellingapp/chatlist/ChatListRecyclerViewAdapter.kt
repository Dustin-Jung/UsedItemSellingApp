package com.android.aop.part2.useditemsellingapp.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.useditemsellingapp.databinding.ItemChatListBinding

class ChatListRecyclerViewAdapter(val onItemClicked: (ChatListItem) -> Unit) : RecyclerView.Adapter<ChatListViewHolder>(){

    private val chatList = mutableListOf<ChatListItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {

        val binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ChatListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(chatList[position])

    }

    override fun getItemCount(): Int {
        return chatList.size
    }


}

class ChatListViewHolder(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(chatListItem: ChatListItem) {
        binding.root.setOnClickListener {
            onItemClicked(chatListItem)
        }

        binding.chatRoomTitleTextView.text = chatListItem.itemTitle

    }
}

