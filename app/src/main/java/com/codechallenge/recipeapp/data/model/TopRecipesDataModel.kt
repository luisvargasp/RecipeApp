package com.codechallenge.recipeapp.data.model

import com.google.gson.annotations.SerializedName

data  class TopRecipesDataModel(
    @SerializedName("code")
    val code:Int ,
    @SerializedName("message")
    val message:String,
    @SerializedName("recipes")
    val recipes:List<RecipeDataModel>
) {
    data class RecipeDataModel(
        @SerializedName("id")
        val id:Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("ingredients")
        val ingredients: List<String>,
        @SerializedName("originLocation")
        val originLocation: LocationDataModel
    )  {
        data class LocationDataModel(
            @SerializedName("name")
            val name: String,
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("longitude")
            val longitude: Double
        )
    }

}