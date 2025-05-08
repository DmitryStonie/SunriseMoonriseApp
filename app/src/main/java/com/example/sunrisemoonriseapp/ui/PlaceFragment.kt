package com.example.sunrisemoonriseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sunrisemoonriseapp.MainActivity
import com.example.sunrisemoonriseapp.MainScreenViewModel
import com.example.sunrisemoonriseapp.R
import com.example.sunrisemoonriseapp.entities.Place
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class PlaceFragment: Fragment() {
    private val viewModel:MainScreenViewModel  by activityViewModels<MainScreenViewModel>()

    private lateinit var mapView: MapView
    private lateinit var backButtonView: View
    private lateinit var selectButtonView: View


    lateinit var inputListener: InputListener
    lateinit var placemarkTapListener: MapObjectTapListener
    lateinit var imageProvider: ImageProvider
    var selectedPlacemark: MapObject? = null
    var place: Place? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false);
    }

    override fun onViewCreated(view: View, savedIntanceState: Bundle?) {
        super.onViewCreated(view, savedIntanceState)
        mapView = view.findViewById<MapView>(R.id.mapview)
        backButtonView = view.findViewById(R.id.back)
        selectButtonView = view.findViewById(R.id.select)

        imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.marker)
        placemarkTapListener = MapObjectTapListener { placemark, point ->
            placemark.isVisible = false
            place = null
            true
        }
        inputListener = object : InputListener {
            override fun onMapTap(
                p0: com.yandex.mapkit.map.Map,
                p1: Point
            ) {
                val placemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = p1
                    setIcon(imageProvider)
                }
                placemark.addTapListener(placemarkTapListener)
                selectedPlacemark?.isVisible = false
                selectedPlacemark = placemark
                place = Place(p1.latitude.toString(), p1.longitude.toString())
            }

            override fun onMapLongTap(
                p0: com.yandex.mapkit.map.Map,
                p1: Point
            ) {
                val placemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = p1
                    setIcon(imageProvider)
                }
                placemark.addTapListener(placemarkTapListener)
                place = Place(p1.latitude.toString(), p1.longitude.toString())
                selectedPlacemark?.isVisible = false
                selectedPlacemark = placemark
                place = Place(p1.latitude.toString(), p1.longitude.toString())
            }
        }
        if(viewModel.placeInfo.value != null){
            place = viewModel.placeInfo.value!!
            selectedPlacemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(place!!.latitude.toDouble(), place!!.longitude.toDouble())
                setIcon(imageProvider)
            }
            selectedPlacemark!!.addTapListener(placemarkTapListener)
        }

        mapView.mapWindow.map.addInputListener(inputListener)
        selectButtonView.setOnClickListener {
            place?.let{
                viewModel.updatePlaceInfo(it)
            }
            parentFragmentManager.popBackStack()
        }
        backButtonView.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
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
}