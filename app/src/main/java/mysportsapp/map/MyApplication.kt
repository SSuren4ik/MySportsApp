package mysportsapp.map

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }
}