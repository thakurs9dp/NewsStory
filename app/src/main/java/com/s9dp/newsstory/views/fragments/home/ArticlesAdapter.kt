package com.s9dp.newsstory.views.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.s9dp.newsstory.databinding.ItemArticlePreviewBinding
import com.s9dp.newsstory.views.utils.extention.loadRectImage


class ArticlesAdapter(private val listener: OnItemClickListener): ListAdapter<NewsData.Article, ArticlesAdapter.ArticleViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val article = getItem(position)
                        listener.onItemClick(article)
                    }
                }
            }
        }

        fun bind(article: NewsData.Article){
            binding.apply {
                ivArticleImage.loadRectImage(article.urlToImage ?: "")
                tvDescription.text = article.description
                tvTitle.text = article.title
                tvPublishedAt.text = article.publishedAt
                tvSource.text = article.source?.name
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(article: NewsData.Article)
    }


    class DiffCallback : DiffUtil.ItemCallback<NewsData.Article>(){
        override fun areItemsTheSame(oldItem: NewsData.Article, newItem: NewsData.Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NewsData.Article, newItem: NewsData.Article): Boolean {
            return oldItem == newItem
        }

    }
}