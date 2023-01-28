package com.codechallenge.recipeapp.domain.usecases

import com.codechallenge.recipeapp.domain.mappers.toEntity
import com.codechallenge.recipeapp.domain.repository.remote.RecipeRepository
import com.codechallenge.recipeapp.presentation.entities.RecipeEntity
import  com.codechallenge.recipeapp.data.util.Result
import javax.inject.Inject

class GetTopRecipesUseCase @Inject constructor (private val repository: RecipeRepository){

    suspend fun execute(): Result<List<RecipeEntity>>{
        return when (val result = repository.getTopRecipes()) {
            is Result.Success -> {
                Result.Success(result.data?.map { it.toEntity() })
            }
            is Result.Error -> {
                Result.Error(result.exception)
            }
        }
    }
}