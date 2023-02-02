package com.codechallenge.recipeapp.presentation.features.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.codechallenge.recipeapp.domain.mappers.toEntity
import com.codechallenge.recipeapp.domain.repository.FakeRecipeRepository
import com.codechallenge.recipeapp.launchFragmentInHiltContainer
import com.codechallenge.recipeapp.util.MainCoroutineRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
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
class MapsFragmentTest {
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
    fun launchMapsFragment(){
        runBlocking {
            val navController = Mockito.mock(NavController::class.java)
            val locationArg= FakeRecipeRepository.pizza.originLocation.toEntity()
            launchFragmentInHiltContainer<MapsFragment>(fragmentArgs = bundleOf("location" to locationArg)) {
                Navigation.setViewNavController(requireView(),navController)

            }
        }
    }
}