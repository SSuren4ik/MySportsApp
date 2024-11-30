package com.map.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.map.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var permissionManager: PermissionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionManager = PermissionManager(
            context = requireContext(),
            onPermissionGranted = { initializeMap() },
            onPermissionDenied = { navigateToPreviousFragment() }
        )
        permissionManager.initialize(this)
        handleLocationPermission()
    }

    private fun handleLocationPermission() {
        permissionManager.checkAndRequestLocationPermission()
    }

    private fun initializeMap() {
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapview
        mapView.onStart()
        Log.d("MapFragment", "Map initialized")
    }

    private fun navigateToPreviousFragment() {
        Toast.makeText(
            requireContext(),
            "Разрешение на доступ к локации не предоставлено.",
            Toast.LENGTH_LONG
        ).show()
        parentFragmentManager.popBackStack()
        Log.d("MapFragment", "Navigated to previous fragment due to missing permission")
    }

    override fun onStart() {
        super.onStart()
        if (permissionManager.hasLocationPermission() && ::mapView.isInitialized) {
            mapView.onStart()
            MapKitFactory.getInstance().onStart()
        }
    }

    override fun onStop() {
        if (::mapView.isInitialized) {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
        super.onStop()
    }
}
