package com.map.presentation

import android.os.Bundle
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
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePermissionManager()
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
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapview
        mapView.onStart()
    }

    private fun navigateToPreviousFragment() {
        showToast("Location permission not granted.")
        parentFragmentManager.popBackStack()
    }

    override fun onStart() {
        super.onStart()
        if (isMapInitialized()) {
            mapView.onStart()
            MapKitFactory.getInstance().onStart()
        }
    }

    override fun onStop() {
        if (isMapInitialized()) {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
        super.onStop()
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }

    private fun isMapInitialized(): Boolean {
        return ::mapView.isInitialized
    }
}
