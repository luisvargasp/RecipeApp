package com.codechallenge.recipeapp.presentation.features.maps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.codechallenge.recipeapp.R
import com.codechallenge.recipeapp.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class MapsFragment : Fragment() , OnMapReadyCallback {
    private lateinit var binding: FragmentMapsBinding
    val args: MapsFragmentArgs by navArgs()
    private lateinit var map: GoogleMap
     var marker:Marker?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)// starts onMapReady
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap
        map.uiSettings.apply {
            isZoomGesturesEnabled = true
            isZoomControlsEnabled = false
            isScrollGesturesEnabled = true
            isTiltGesturesEnabled = false
            isRotateGesturesEnabled = false
            isMyLocationButtonEnabled = false
            isMapToolbarEnabled = false
            isCompassEnabled = false
        }
        val latLng= LatLng(args.location.latitude,args.location.longitude)
        marker=map.addMarker(
            MarkerOptions().position(latLng).title(args.location.name)
        )!!
        map.animateCamera(CameraUpdateFactory.newCameraPosition(
            Builder()
                .target(latLng)
                .zoom(10f)
                .build()))

    }

}