package com.map.domain.repository

import com.yandex.mapkit.location.LocationManager

interface LocationRepositoryInterface {

    fun getLocationManager(): LocationManager
}