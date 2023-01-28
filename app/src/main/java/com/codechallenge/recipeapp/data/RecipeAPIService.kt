package com.codechallenge.recipeapp.data

import com.codechallenge.recipeapp.data.model.TopRecipesDataModel
import retrofit2.Response
import retrofit2.http.GET

interface RecipeAPIService {
    @GET("recipe/tops")
    suspend fun getTopRecipes():Response<TopRecipesDataModel>
}