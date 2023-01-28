package com.codechallenge.recipeapp.domain.repositoryimpl.remote

import com.codechallenge.recipeapp.data.RecipeAPIService
import com.codechallenge.recipeapp.data.mappers.toDomainModel
import com.codechallenge.recipeapp.data.util.ErrorUtil.handleApiService
import com.codechallenge.recipeapp.domain.model.Recipe
import com.codechallenge.recipeapp.data.util.Result

import com.codechallenge.recipeapp.domain.repository.remote.RecipeRepository

class RecipeRepositoryImpl(val api:RecipeAPIService): RecipeRepository {
    override suspend fun getTopRecipes(): Result<List<Recipe>>  {
        return handleApiService {
            val response = api.getTopRecipes()
            val body = response.body()
            result(response, body?.code, body?.message) {
                val data = body?.recipes?.map { it.toDomainModel() }
                data
            }
        }

    }
}