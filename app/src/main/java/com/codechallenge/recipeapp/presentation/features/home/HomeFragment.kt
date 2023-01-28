package com.codechallenge.recipeapp.presentation.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codechallenge.recipeapp.databinding.FragmentHomeBinding
import com.codechallenge.recipeapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: RecipeAdapter
    lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        setupAdapter()
        subscribeToObservers()
        setupSearchView()
    }

    private fun subscribeToObservers(){
        viewModel.recipesToShow.observe(viewLifecycleOwner
        ){
            it?.let {list->
                adapter.recipes=list
            }

        }
        viewModel.errorException.observe(viewLifecycleOwner
        ){
            it?.let {exception ->
                showMessageByException(exception)
            }

        }

    }
    private fun setupAdapter(){
        adapter= RecipeAdapter()
        adapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.toRecipeDetail(it))
        }
        binding.rvRecipe.adapter=adapter

    }
    private fun setupSearchView(){
        var job: Job? = null
        binding.svRecipe.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(500)
                    newText.let {
                            viewModel.searchRecipe(it.lowercase())
                    }
                }

                return false
            }
        })

    }
}