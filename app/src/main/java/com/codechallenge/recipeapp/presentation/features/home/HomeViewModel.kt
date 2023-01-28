package com.codechallenge.recipeapp.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codechallenge.recipeapp.domain.usecases.GetTopRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.codechallenge.recipeapp.data.util.Result
import com.codechallenge.recipeapp.presentation.entities.RecipeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(val useCase: GetTopRecipesUseCase):ViewModel() {

    private val _topRecipes = MutableLiveData<List<RecipeEntity>>()
    val topRecipes: LiveData<List<RecipeEntity>> get() = _topRecipes

    private val _recipesToShow = MutableLiveData<List<RecipeEntity>>()
    val recipesToShow: LiveData<List<RecipeEntity>> get() = _recipesToShow

    val isLoading = MutableLiveData(false)
    val listSuccessFullyLoaded= MutableLiveData(false)
    val showNoRecipesFoundInSearch=MutableLiveData(false)
    val showErrorState=MutableLiveData(false)
    val errorException = MutableLiveData<Exception>()


    init {
        retrieveTopRecipes()

    }
    fun retrieveTopRecipes(){
       isLoading.value=true
       listSuccessFullyLoaded.value=false
       showErrorState.value=false
       viewModelScope.launch (Dispatchers.IO){

           when (val result = useCase.execute()) {
               is Result.Success -> {
                   _topRecipes.postValue(result.data!!)
                   _recipesToShow.postValue(result.data!!)
                   withContext(Dispatchers.Main){
                       listSuccessFullyLoaded.value=result.data.isNotEmpty()
                   }
               }
               is Result.Error -> {
                   withContext(Dispatchers.Main){
                       listSuccessFullyLoaded.value=false
                       showErrorState.value=true
                       errorException.value=result.exception
                       errorException.value=null
                   }
               }

           }
           withContext(Dispatchers.Main){
               isLoading.value=false
           }
       }

    }

    fun searchRecipe(query: String) {
        if(query.isEmpty()){
            showNoRecipesFoundInSearch.value=false
            _recipesToShow.value=_topRecipes.value
        }else if( query.length>=3) {
           val filteredList=_topRecipes.value?.filter { recipe -> recipe.name.lowercase().contains(query) || recipe.ingredients.any{it.lowercase().contains(query)} }
            _recipesToShow.value=filteredList!!
            showNoRecipesFoundInSearch.value=filteredList.isEmpty()
        }

    }


}