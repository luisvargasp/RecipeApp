package com.codechallenge.recipeapp.presentation.features.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codechallenge.recipeapp.databinding.FragmentHomeBinding
import com.codechallenge.recipeapp.databinding.FragmentMapsBinding


class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}