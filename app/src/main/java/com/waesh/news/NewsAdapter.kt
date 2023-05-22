package com.waesh.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.size.Scale
import com.waesh.news.databinding.ItemBinding

class NewsAdapter(private val clickListener: ItemClickListener): ListAdapter<NewsArticles.Article, NewsAdapter.NewsViewHolder>(Comparator()) {

    class NewsViewHolder(val binding: ItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding: ItemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.tvHeadline.text = currentList[position].title
        holder.binding.tvContent.text = currentList[position].content
        holder.binding.tvPublishedAt.text = currentList[position].publishedAt
        holder.binding.tvSource.text = currentList[position].source.name
        holder.binding.ivPhoto.load(currentList[position].urlToImage){
            scale(Scale.FILL)
        }
        holder.itemView.setOnClickListener{
            clickListener.onClick(currentList[position])
        }
    }
}

class Comparator: DiffUtil.ItemCallback<NewsArticles.Article>(){
    override fun areItemsTheSame(
        oldItem: NewsArticles.Article,
        newItem: NewsArticles.Article
    ): Boolean {
        return newItem.url == oldItem.url
    }

    override fun areContentsTheSame(
        oldItem: NewsArticles.Article,
        newItem: NewsArticles.Article
    ): Boolean {
        return newItem == oldItem
    }
}

interface ItemClickListener{
    fun onClick(item: NewsArticles.Article)
}