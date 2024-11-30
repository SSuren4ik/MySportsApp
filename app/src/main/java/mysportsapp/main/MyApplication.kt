package mysportsapp.main

import android.app.Application
import android.util.Log
import com.map.BuildConfig
import com.yandex.mapkit.MapKitFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Log", "MyApplication")
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}