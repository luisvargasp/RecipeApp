package com.codechallenge.recipeapp.domain.mappers

import com.codechallenge.recipeapp.data.model.TopRecipesDataModel
import com.codechallenge.recipeapp.domain.model.Recipe
import com.codechallenge.recipeapp.presentation.entities.RecipeEntity

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id=this.id,
        name=this.name,
        description=this.description,
        imageUrl=this.imageUrl,
        ingredients=this.ingredients,
        originLocation=this.originLocation.toEntity()
    )
}

fun Recipe.Location.toEntity(): RecipeEntity.LocationEntity {
    return RecipeEntity.LocationEntity(name = this.name, latitude = this.latitude, longitude = this.longitude)
}