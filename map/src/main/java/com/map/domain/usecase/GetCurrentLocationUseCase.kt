package com.map.domain.usecase

import android.util.Log
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.location.Purpose

class GetCurrentLocationUseCase(private val locationManager: LocationManager) {

    private lateinit var locationListener: LocationListener

    fun subscribeLocationUpdates(
        onLocationUpdated: (Location) -> Unit,
    ) {
        locationListener = object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                Log.d("GetCurrentLocationUseCase", "onLocationUpdated: ${location.position}")
                onLocationUpdated(location)
            }

            override fun onLocationStatusUpdated(locationStatus: LocationStatus) {
                if (locationStatus != LocationStatus.AVAILABLE) {
                    Log.w("GetCurrentLocationUseCase", "Location unavailable")
                }
            }
        }

        locationManager.subscribeForLocationUpdates(
            0.0,
            1000,
            50.0,
            false,
            FilteringMode.OFF,
            Purpose.NAVIGATION,
            locationListener
        )
    }

    fun stopLocationUpdates() {
        if (::locationListener.isInitialized) {
            Log.d("GetCurrentLocationUseCase", "stopLocationUpdates")
            locationManager.unsubscribe(locationListener)
        }
    }
}