package com.codechallenge.recipeapp.presentation.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.codechallenge.recipeapp.R
import com.codechallenge.recipeapp.domain.mappers.toEntity
import com.codechallenge.recipeapp.domain.repository.FakeRecipeRepository
import com.codechallenge.recipeapp.getOrAwaitValueTest
import com.codechallenge.recipeapp.launchFragmentInHiltContainer
import com.codechallenge.recipeapp.util.MainCoroutineRule
import com.codechallenge.recipeapp.util.RecyclerViewMatchers.hasItemCount
import com.codechallenge.recipeapp.util.TestFragmentFactory
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
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
class HomeFragmentTest {
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
    fun launchHomeFragment_SuccessCase(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
             lateinit var testViewModel:HomeViewModel
           launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
               testViewModel=viewModel
            }

            //advanceUntilIdle()
            //CheckVisibilities
            onView(withId(R.id.rvRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            onView(withId(R.id.svRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

            assertThat(testViewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isTrue()

        }
    }
    @Test
    fun verifyNavigationToDetailFragment(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }
            //advanceUntilIdle()

            onView( withId(R.id.rvRecipe)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecipeAdapter.RecipeViewHolder>(
                    0, ViewActions.click()
                )
            )
            Mockito.verify(navController).navigate(
                HomeFragmentDirections.toRecipeDetail(testViewModel.selectedRecipe.getOrAwaitValueTest()!!)
            )
        }
    }
    @Test
    fun launchHomeFragment_ErrorCase(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory(shouldReturnError = true)) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }

            //advanceUntilIdle()
            //Check views
            onView(withId(R.id.rvRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.svRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.ibRetry)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            assertThat(testViewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isFalse()
            assertThat(testViewModel.showErrorState.getOrAwaitValueTest()).isTrue()

        }
    }
    @Test
    fun launchHomeFragment_ErrorCase_Then_Click_On_RetryButton(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel: HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(
                factory = TestFragmentFactory(
                    shouldReturnError = true
                )
            ) {
                Navigation.setViewNavController(requireView(), navController)
                testViewModel = viewModel
            }

            //advanceUntilIdle()
            //errorCase
            onView(withId(R.id.rvRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.svRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.ibRetry)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            assertThat(testViewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isFalse()
            assertThat(testViewModel.showErrorState.getOrAwaitValueTest()).isTrue()
             GlobalScope.launch {
            onView(withId(R.id.ibRetry)).perform(click())//Suspend Function

         }
            //getting ErrorCase again given that the result will be the same
            onView(withId(R.id.rvRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.svRecipe)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
            onView(withId(R.id.ibRetry)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            assertThat(testViewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isFalse()
            assertThat(testViewModel.showErrorState.getOrAwaitValueTest()).isTrue()

        }
    }
    @Test
    fun launchHomeFragment_andVerifyClickRecipe(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }
           // advanceUntilIdle()
            onView( withId(R.id.rvRecipe)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecipeAdapter.RecipeViewHolder>(
                    0, ViewActions.click()//Click in first element
                ))
            //Pizza is the first element of the fake list
            assertThat(testViewModel.selectedRecipe.getOrAwaitValueTest()).isEqualTo(
                FakeRecipeRepository.pizza.toEntity())

        }
    }


    @Test
    fun testSearcher_Query_Sushi_Should_return_and_show_only_one_item(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }
            //advanceUntilIdle()
            testViewModel.searchRecipe2("Sushi")

            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).hasSize(1)
            onView(withId(R.id.rvRecipe)).check(matches(hasItemCount(1)))

        }
    }
    @Test
    fun testSearcher_Query_Makis_Should_return_empty_list_and_show_Not_recipes_found_textview(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }
            //advanceUntilIdle()
            testViewModel.searchRecipe2("Makis")

            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).hasSize(0)
            onView(withId(R.id.rvRecipe)).check(matches(hasItemCount(0)))
            onView(withId(R.id.tvNoRecipesFound)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        }
    }
    @Test
    fun testSearcher_Query_PESCADO_Should_return_a_3_element_List(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            lateinit var testViewModel:HomeViewModel
            launchFragmentInHiltContainer<HomeFragment>(factory = TestFragmentFactory()) {
                Navigation.setViewNavController(requireView(),navController)
                testViewModel=viewModel
            }
            //advanceUntilIdle()

            testViewModel.searchRecipe2("Pescado")

            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).hasSize(3)
            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).contains(
                FakeRecipeRepository.paella.toEntity())
            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).contains(
                FakeRecipeRepository.sushi.toEntity())
            assertThat(testViewModel.recipesToShow.getOrAwaitValueTest()).contains(
                FakeRecipeRepository.ceviche.toEntity())
            onView(withId(R.id.rvRecipe)).check(matches(hasItemCount(3)))

        }
    }




}