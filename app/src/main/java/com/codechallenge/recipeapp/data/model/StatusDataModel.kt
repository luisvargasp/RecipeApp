package com.codechallenge.recipeapp.data.model

import com.google.gson.annotations.SerializedName

data class StatusDataModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
)