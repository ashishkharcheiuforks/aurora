package com.czxbnb.aurora.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.czxbnb.aurora.R
import com.czxbnb.aurora.databinding.ItemNewsBinding
import com.czxbnb.aurora.model.news.Article
import com.czxbnb.aurora.model.news.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var news: News
    private lateinit var binding: ItemNewsBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.item_news, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news.articles[position])
    }

    override fun getItemCount(): Int {
        return if (::news.isInitialized) news.articles.size else 0
    }

    fun updateNews(news: News) {
        this.news = news
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemNewsBinding) :
            RecyclerView.ViewHolder(binding.root) {
        private val viewModel = NewsItemViewModel()

        fun bind(article: Article) {
            viewModel.bind(article)
            binding.viewModel = viewModel
        }
    }
}