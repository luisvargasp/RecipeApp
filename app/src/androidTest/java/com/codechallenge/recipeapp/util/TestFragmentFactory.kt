package com.codechallenge.recipeapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.codechallenge.recipeapp.domain.repository.FakeRecipeRepository
import com.codechallenge.recipeapp.domain.usecases.GetTopRecipesUseCase
import com.codechallenge.recipeapp.presentation.features.home.HomeFragment
import com.codechallenge.recipeapp.presentation.features.home.HomeViewModel

class TestFragmentFactory (

val shouldReturnError:Boolean?=false
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            HomeFragment::class.java.name -> HomeFragment(HomeViewModel(GetTopRecipesUseCase(
                FakeRecipeRepository().setShouldReturnError(shouldReturnError?:false))))
            else -> super.instantiate(classLoader, className)
        }
    }
}