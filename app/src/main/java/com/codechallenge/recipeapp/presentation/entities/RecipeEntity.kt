package com.codechallenge.recipeapp.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeEntity(
    val id:Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val originLocation: LocationEntity
) :Parcelable{
    @Parcelize
    data class LocationEntity(
        val name: String,
        val latitude: Double,
        val longitude: Double
    ):Parcelable
}

