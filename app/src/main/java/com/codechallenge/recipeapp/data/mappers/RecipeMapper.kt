package com.codechallenge.recipeapp.data.mappers

import com.codechallenge.recipeapp.data.model.TopRecipesDataModel
import com.codechallenge.recipeapp.domain.model.Recipe


fun TopRecipesDataModel.RecipeDataModel.toDomainModel(): Recipe {
    return Recipe(
        id=this.id,
        name=this.name,
        description=this.description,
        imageUrl=this.imageUrl,
        ingredients=this.ingredients,
        originLocation=this.originLocation.toDomainModel()
    )
}

fun TopRecipesDataModel.RecipeDataModel.LocationDataModel.toDomainModel(): Recipe.Location {
    return Recipe.Location(name = this.name, latitude = this.latitude, longitude = this.longitude)
}