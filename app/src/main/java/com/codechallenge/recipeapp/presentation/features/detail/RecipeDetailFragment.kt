package com.codechallenge.recipeapp.presentation.features.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.codechallenge.recipeapp.databinding.FragmentRecipeDetailBinding


class RecipeDetailFragment : Fragment() {

    private val args: RecipeDetailFragmentArgs by navArgs()


    private lateinit var binding: FragmentRecipeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this
        binding.recipe=args.recipe
        binding.ibLocation.setOnClickListener{
            findNavController().navigate(RecipeDetailFragmentDirections.toLocationMaps(args.recipe.originLocation))
        }


    }
}