package com.map.data

import com.map.domain.repository.LocationRepositoryInterface
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.LocationManager

class LocationRepositoryImpl : LocationRepositoryInterface {

    override fun getLocationManager(): LocationManager {
        return MapKitFactory.getInstance().createLocationManager()
    }
}