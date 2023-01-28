package com.codechallenge.recipeapp.presentation.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codechallenge.recipeapp.databinding.RecipeItemListBinding
import com.codechallenge.recipeapp.presentation.entities.RecipeEntity

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var onItemClickListener: ((RecipeEntity) -> Unit)? = null

    inner class RecipeViewHolder(val binding: RecipeItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeEntity) {
            Glide.with(binding.root.context).load(recipe.imageUrl).into(binding.ivDish)
            binding.tvTitle.text=recipe.name
            binding.tvDescription.text=recipe.description
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(recipe)
                }
            }
        }

    }

    private val diffUtil = object : DiffUtil.ItemCallback<RecipeEntity>() {
        override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var recipes: List<RecipeEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        val binding = RecipeItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }


    fun setOnItemClickListener(listener: (RecipeEntity) -> Unit) {
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes.get(position))
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

}