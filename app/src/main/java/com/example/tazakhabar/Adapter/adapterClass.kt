package com.example.tazakhabar.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.tazakhabar.databinding.SinglenewsitemBinding
import com.example.tazakhabar.modelClasses.Article

class adapterClass(var list_of_News: ArrayList<Article>) :
    RecyclerView.Adapter<adapterClass.ClassViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val newsItemBinder =
            SinglenewsitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClassViewHolder(newsItemBinder)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val position_of_information_in_model = list_of_News[position]
        holder.bindViews(position_of_information_in_model)
        holder.itemView.rootView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(position_of_information_in_model.url))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list_of_News.size
    }

//By passing the root view to the superclass constructor,
// we are telling the RecyclerView that this ViewHolder should be associated with this particular layout file.

    class ClassViewHolder(private var singlenewsitemBinding: SinglenewsitemBinding) :
        ViewHolder(singlenewsitemBinding.root) {
        fun bindViews(article: Article) {
            singlenewsitemBinding.heading.text = article.title
            singlenewsitemBinding.Author.text = article.source.name
            singlenewsitemBinding.Content.text = article.description
            singlenewsitemBinding.PublishedAt.text = article.publishedAt
            Glide.with(singlenewsitemBinding.imageNews.context).load(article.image)
                .into(singlenewsitemBinding.imageNews)
        }

    }

}