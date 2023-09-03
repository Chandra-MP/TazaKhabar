package com.example.tazakhabar.Adapter

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.tazakhabar.R
import com.example.tazakhabar.databinding.CategorylisitemBinding
import com.example.tazakhabar.modelClasses.categoryList
import kotlin.random.Random

class categoryListAdapter(
    var list_of_cat: ArrayList<categoryList>,
    var categoryClicked: (categoryList) -> Unit
) :
    RecyclerView.Adapter<categoryListAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(private val categoryListItemBinding: CategorylisitemBinding) :
        ViewHolder(categoryListItemBinding.root) {


        fun bind(categoryList: categoryList) {
            categoryListItemBinding.categoryName.text = categoryList.categoryName
            categoryListItemBinding.cardBusiness.tag = categoryList.categoryTag
            Glide.with(categoryListItemBinding.innerImage.context).load(categoryList.Icons)
                .into(categoryListItemBinding.innerImage)
            categoryListItemBinding.cardBusiness.setCardBackgroundColor(categoryListItemBinding.root.resources.getColor(
                categoryList.color,
                null))
            //The PorterDuff.Mode.SRC_IN mode specifies that the new color should replace the original color of the vector image.
            val colorFilter =
                PorterDuffColorFilter(ContextCompat.getColor(categoryListItemBinding.innerImage.context,
                    categoryList.color), PorterDuff.Mode.SRC_IN)
            categoryListItemBinding.innerImage.colorFilter = colorFilter
            categoryListItemBinding.cardBusiness.setOnClickListener {
                categoryClicked(categoryList)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflatedLayout =
            CategorylisitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(inflatedLayout)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = list_of_cat[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return list_of_cat.size
    }

}