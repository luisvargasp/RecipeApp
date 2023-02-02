package com.codechallenge.recipeapp.domain.repository

import com.codechallenge.recipeapp.data.util.Result
import com.codechallenge.recipeapp.domain.model.Recipe
import com.codechallenge.recipeapp.domain.repository.remote.RecipeRepository

class FakeRecipeRepository :RecipeRepository{
    companion object{
      val ceviche=  Recipe(id=1,"Ceviche","Comida de Peru","url", listOf("Pescado","Limon","Cebolla"),Recipe.Location("Lima",12.4,45.6))
       val sushi= Recipe(id=2,"Sushi","Comida de Japon","url", listOf("Pescado","Arroz","Azucar"),Recipe.Location("Tokio",12.4,45.6))
        val paella=Recipe(id=3,"Paella","Comida de España","url", listOf("Arroz","Pescado","Mariscos"),Recipe.Location("Valencia",12.4,45.6))
       val pizza= Recipe(id=4,"Pizza","Comida de Italia","url", listOf("Harina","Queso","Tomates"),Recipe.Location("Roma",12.4,45.6))
       val tacos= Recipe(id=5,"Tacos","Comida de Mexico","url", listOf("Harina","Carne","Cebolla"),Recipe.Location("Mexico",12.4,45.6))

        val recipes= listOf(
            pizza,
            sushi,
            paella,
            ceviche,
            tacos
        )
    }


    private var shouldReturnError=false
     fun setShouldReturnError(value:Boolean): FakeRecipeRepository {
         shouldReturnError=value
         return this
     }
    override suspend fun getTopRecipes(): Result<List<Recipe>> {
        return if(shouldReturnError){
            Result.Error(Exception("Error"))
        }else{
            Result.Success(recipes)
        }
    }
}