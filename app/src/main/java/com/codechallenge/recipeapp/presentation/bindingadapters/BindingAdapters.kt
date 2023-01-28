package com.codechallenge.recipeapp.presentation.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setVisibility")
fun View.setVisibility(show:Boolean){
    this.visibility=if(show) View.VISIBLE else View.GONE
}
@BindingAdapter("setImageFromUrl")
fun ImageView.loadImage(imageUrl:String){
    Glide.with(this.context).load(imageUrl).into(this)
}

@BindingAdapter("setTextFromList")
fun TextView.setTextFromList(list:List<String>){
    this.text=list.joinToString( separator = "\n"){
        "-$it"
    }
}