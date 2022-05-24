package com.android.aop.part2.useditemsellingapp.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.useditemsellingapp.databinding.ItemChatBinding
import com.android.aop.part2.useditemsellingapp.home.ArticleModel

class ChatItemRecyclerViewAdapter:RecyclerView.Adapter<ChatItemViewHolder>() {

    private val chatItem = mutableListOf<ChatItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ChatItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        holder.bind(chatItem[position])
    }

    override fun getItemCount(): Int {
        return chatItem.size
    }

    fun addAll(list: List<ChatItem>) {

        chatItem.addAll(list)

        notifyDataSetChanged()
    }

}

class ChatItemViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(chatItem: ChatItem) {
        binding.senderTextView.text = chatItem.senderId
        binding.messageTextView.text = chatItem.message

    }
}