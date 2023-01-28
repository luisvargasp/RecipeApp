package com.codechallenge.recipeapp.domain.repository.remote

import com.codechallenge.recipeapp.data.util.Result
import com.codechallenge.recipeapp.domain.model.Recipe

interface RecipeRepository {
    suspend fun getTopRecipes() :Result<List<Recipe>>
}