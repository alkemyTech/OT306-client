package com.melvin.ongandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.NewListBinding
import com.melvin.ongandroid.model.news.News

class NewsAdapter(private val newslist: List<News>): RecyclerView.Adapter<NewsAdapter.Newsviewholder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Newsviewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewListBinding.inflate(layoutInflater, parent, false)
        return Newsviewholder(binding)
    }

    override fun onBindViewHolder(holder: Newsviewholder, position: Int) {

    }

    override fun getItemCount(): Int {
        //return newslist.size
        return 1
    }
    inner class Newsviewholder(private val binding: NewListBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(news: News){
            binding.tvNameNews.text = news.name

        }

    }


}