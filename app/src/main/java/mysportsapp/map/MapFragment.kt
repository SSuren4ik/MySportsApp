package mysportsapp.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mysportsapp.R
import com.example.mysportsapp.databinding.FragmentMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapFragment : Fragment(), CameraListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private val startLocation = Point(59.9402, 30.315)
    private var zoomValue: Float = 15f
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapview
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToStartLocation()
        setMarkerInStartLocation()
        binding.mapview.mapWindow.map.addCameraListener(this)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun moveToStartLocation() {
        binding.mapview.mapWindow.map.move(
            CameraPosition(startLocation, zoomValue, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5f),
            null
        )
    }

    private fun setMarkerInStartLocation() {
        val marker = createBitmapFromVector(R.drawable.baseline_location_pin_24)
        val imageProvider = ImageProvider.fromBitmap(marker)

        mapObjectCollection = binding.mapview.mapWindow.map.mapObjects

        placemarkMapObject = mapObjectCollection.addPlacemark(startLocation, imageProvider)
        with(placemarkMapObject) {
            setText("Обязательно к посещению!")
            opacity = 0.5f
        }

    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean,
    ) {
        zoomValue = cameraPosition.zoom
    }

    companion object {
        fun newInstance() = MapFragment()
        const val ZOOM_BOUNDARY = 16.4f
    }
}
