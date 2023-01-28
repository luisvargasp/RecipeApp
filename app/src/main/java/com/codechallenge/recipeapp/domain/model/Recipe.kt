package com.codechallenge.recipeapp.domain.model


data class Recipe(
    val id:Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val originLocation: Location
)  {
    data class Location(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )
}