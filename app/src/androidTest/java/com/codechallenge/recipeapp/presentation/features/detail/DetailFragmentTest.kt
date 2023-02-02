package com.codechallenge.recipeapp.presentation.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.codechallenge.recipeapp.R
import com.codechallenge.recipeapp.domain.mappers.toEntity
import com.codechallenge.recipeapp.domain.repository.FakeRecipeRepository
import com.codechallenge.recipeapp.launchFragmentInHiltContainer
import com.codechallenge.recipeapp.presentation.bindingadapters.getTextFromList
import com.codechallenge.recipeapp.presentation.features.home.HomeViewModel
import com.codechallenge.recipeapp.util.MainCoroutineRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    @Before
    fun init(){
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun launchRecipeDetailFragment(){
        runTest {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel: HomeViewModel
            launchFragmentInHiltContainer<RecipeDetailFragment>(fragmentArgs = bundleOf("recipe" to FakeRecipeRepository.pizza.toEntity())) {
                Navigation.setViewNavController(requireView(),navController)
            }
        }
    }
    @Test
    fun launchRecipeDetailFragment_and_Check_information(){
        runTest {
            val navController = Mockito.mock(NavController::class.java)
            val recipeArg= FakeRecipeRepository.pizza.toEntity()
            launchFragmentInHiltContainer<RecipeDetailFragment>(fragmentArgs = bundleOf("recipe" to recipeArg)) {
                Navigation.setViewNavController(requireView(),navController)
            }
            Espresso.onView(ViewMatchers.withId(R.id.tvTitle))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipeArg.name)));
            Espresso.onView(ViewMatchers.withId(R.id.tvDescription))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipeArg.description)));
            Espresso.onView(ViewMatchers.withId(R.id.tvOriginLocation))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipeArg.originLocation.name)));
            Espresso.onView(ViewMatchers.withId(R.id.tvIngredients))
                .check(ViewAssertions.matches(ViewMatchers.withText(recipeArg.ingredients.getTextFromList())));
        }
    }
    @Test
    fun launchRecipeDetailFragment_and_Check_Navigation_to_Map_Fragment(){
        runTest {
            val navController = Mockito.mock(NavController::class.java)
            val recipeArg= FakeRecipeRepository.pizza.toEntity()
            launchFragmentInHiltContainer<RecipeDetailFragment>(fragmentArgs = bundleOf("recipe" to recipeArg)) {
                Navigation.setViewNavController(requireView(),navController)
            }
            Espresso.onView(ViewMatchers.withId(R.id.ibLocation)).perform(ViewActions.click())
            Mockito.verify(navController).navigate(
                RecipeDetailFragmentDirections.toLocationMaps(recipeArg.originLocation)
            )
        }
    }
}