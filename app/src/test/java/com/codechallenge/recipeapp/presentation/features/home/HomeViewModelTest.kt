package com.codechallenge.recipeapp.presentation.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codechallenge.recipeapp.repository.FakeRecipeRepository
import com.codechallenge.recipeapp.util.MainCoroutineRule
import com.codechallenge.recipeapp.domain.mappers.toEntity
import com.codechallenge.recipeapp.domain.usecases.GetTopRecipesUseCase
import com.codechallenge.recipeapp.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest{
    private  lateinit var viewModel: HomeViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Before
    fun setup(){
            Dispatchers.setMain(Dispatchers.Unconfined)
            viewModel= HomeViewModel(GetTopRecipesUseCase(FakeRecipeRepository()))
    }
    @Test
    fun `get recipes from remote  should return ok `(){
        runTest {
            advanceUntilIdle()
            assertThat(viewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isTrue()
        }

    }
    @Test
    fun `get recipes from remote  should return error when an  error occurs `(){
        viewModel= HomeViewModel(GetTopRecipesUseCase(FakeRecipeRepository().setShouldReturnError(true)))
        runTest {
            advanceUntilIdle()
            assertThat(viewModel.listSuccessFullyLoaded.getOrAwaitValueTest()).isFalse()
        }
    }
    @Test
    fun `given a 2 character query  it should not filtrate`(){
        runTest {
            assertThat(viewModel.shouldFiltrate("co")).isFalse()
        }

    }
    @Test
    fun `given a empty  query it should not filtrate`(){
        runTest {
            assertThat(viewModel.shouldFiltrate("")).isFalse()
        }

    }
    @Test
    fun `given a empty  query should  filtrate if a previous filter was applied`(){
        runTest {
            advanceUntilIdle()
            viewModel.searchRecipe2("Cevi")
            assertThat(viewModel.shouldFiltrate("")).isTrue()
        }
    }
    @Test
    fun `Filter by name  , given a   query (Sushi)  the filtered list should contain such dish `(){
        runTest {
            advanceUntilIdle()
            viewModel.searchRecipe2("Sushi")
            assertThat(viewModel.recipesToShow.getOrAwaitValueTest()).contains(FakeRecipeRepository.sushi.toEntity())
        }
    }
    @Test
    fun `Filter by Ingredients ,  given a   query (Pescado)  the filtered list should contain  Ceviche, Paella, and Sushi `(){
        runTest {
            advanceUntilIdle()
            viewModel.searchRecipe2("Pescado")
            assertThat(viewModel.recipesToShow.getOrAwaitValueTest()).contains(FakeRecipeRepository.sushi.toEntity())
            assertThat(viewModel.recipesToShow.getOrAwaitValueTest()).contains(FakeRecipeRepository.ceviche.toEntity())
            assertThat(viewModel.recipesToShow.getOrAwaitValueTest()).contains(FakeRecipeRepository.paella.toEntity())
        }
    }
    @Test
    fun `given a   query (Ravioles)  ,the filtered list should be empty since the recipe list does not contain it`(){

        runTest {
            advanceUntilIdle()
            viewModel.searchRecipe2("Ravioles")
            assertThat(viewModel.recipesToShow.getOrAwaitValueTest()).isEmpty()
            assertThat(viewModel.showNoRecipesFoundInSearch.getOrAwaitValueTest()).isTrue()
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



}