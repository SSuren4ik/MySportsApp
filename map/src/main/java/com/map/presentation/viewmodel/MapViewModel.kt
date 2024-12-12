package com.map.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.map.domain.usecase.GetCurrentLocationUseCase
import com.yandex.mapkit.location.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val getCurrentLocationUseCase: GetCurrentLocationUseCase) : ViewModel() {

    private val _locationFlow = MutableStateFlow<Location?>(null)
    val locationFlow: StateFlow<Location?> = _locationFlow

    private var lastKnownLocation: Location? = null
    private var isFirstLocationUpdate = true

    fun startLocationUpdates() {
        viewModelScope.launch {
            getCurrentLocationUseCase.subscribeLocationUpdates { location ->
                if (isFirstLocationUpdate) {
                    _locationFlow.value = location
                    isFirstLocationUpdate = false
                }
                lastKnownLocation = location
            }
        }
    }

    fun stopLocationUpdates() {
        getCurrentLocationUseCase.stopLocationUpdates()
    }

    fun getLastKnownLocation(): Location? = lastKnownLocation
}
