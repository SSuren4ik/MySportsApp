package mysportsapp.main

import android.app.Application
import com.example.mysportsapp.BuildConfig
import com.yandex.mapkit.MapKitFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}