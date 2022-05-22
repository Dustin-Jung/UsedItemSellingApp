package com.android.aop.part2.useditemsellingapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.useditemsellingapp.databinding.ItemArticleRecyclerviewBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ArticleRecyclerViewAdapter(val onItemClicked: (ArticleModel) -> Unit) : RecyclerView.Adapter<ArticleViewHolder>() {

    private val articleList = mutableListOf<ArticleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val binding = ItemArticleRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleList[position],onItemClicked)
    }

    override fun getItemCount(): Int {

        return articleList.size
    }

    fun addAll(list: List<ArticleModel>) {
        articleList.addAll(list)
        notifyDataSetChanged()
    }
}

class ArticleViewHolder(val binding: ItemArticleRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){


    fun bind(articleModel: ArticleModel , onItemClicked: (ArticleModel) -> Unit){
        val format = SimpleDateFormat("MM월 dd일")
        val date = Date(articleModel.createdAt)

        binding.titleTextView.text = articleModel.title
        binding.dateTextView.text = format.format(date).toString()
        binding.priceTextView.text = articleModel.price

        if (articleModel.imageUrl.isNotEmpty()) {
            Glide.with(binding.thumbnailImageView)
                .load(articleModel.imageUrl)
                .into(binding.thumbnailImageView)
        }

        binding.root.setOnClickListener {
            onItemClicked(articleModel)
        }
    }
}