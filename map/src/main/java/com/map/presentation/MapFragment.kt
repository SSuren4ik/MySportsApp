package com.map.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.map.data.LocationRepositoryImpl
import com.map.databinding.FragmentMapBinding
import com.map.domain.usecase.GetCurrentLocationUseCase
import com.map.presentation.viewmodel.MapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.launch

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var permissionManager: PermissionManager
    private lateinit var viewModel: MapViewModel
    private lateinit var mapKit: MapKit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePermissionManager()
        initializeViewModel()

        mapKit.createUserLocationLayer(mapView.mapWindow).isVisible = true

        lifecycleScope.launch {
            viewModel.locationFlow.collect { location ->
                if (location != null) {
                    moveCameraToLocation(location)
                }
            }
        }

        binding.myLocationButton.setOnClickListener {
            val location = viewModel.getLastKnownLocation()
            if (location != null) {
                moveCameraToLocation(location)
            } else {
                showToast("Местоположение недоступно.")
            }
        }
    }

    private fun initializeViewModel() {
        val repository = LocationRepositoryImpl()
        val locationManager = repository.getLocationManager()
        val locationUseCase = GetCurrentLocationUseCase(locationManager)
        viewModel = MapViewModel(locationUseCase)
        viewModel.startLocationUpdates()
    }

    private fun initializePermissionManager() {
        permissionManager = PermissionManager(context = requireContext(),
            onPermissionGranted = { initializeMap() },
            onPermissionDenied = { navigateToPreviousFragment() }).apply {
            initialize(this@MapFragment)
            checkAndRequestLocationPermission()
        }
    }

    private fun initializeMap() {
        if (isMapInitialized()) {
            return
        }
        Log.d("MapFragment", "initializeMap")
        MapKitFactory.initialize(requireContext())
        mapKit = MapKitFactory.getInstance()
        mapView = binding.mapview
        mapView.onStart()
    }

    private fun moveCameraToLocation(location: Location) {
        Log.d("MapFragment", "moveCameraToLocation: $location")
        mapView.mapWindow.map.move(
            CameraPosition(location.position, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )
    }

    private fun navigateToPreviousFragment() {
        showToast("Location permission not granted.")
        parentFragmentManager.popBackStack()
    }

    override fun onStart() {
        super.onStart()
        if (isMapInitialized()) {
            mapView.onStart()
            mapKit.onStart()
        }
    }

    override fun onStop() {
        if (isMapInitialized()) {
            mapView.onStop()
            mapKit.onStop()
        }
        viewModel.stopLocationUpdates()
        super.onStop()
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    private fun isMapInitialized(): Boolean {
        return ::mapView.isInitialized
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}