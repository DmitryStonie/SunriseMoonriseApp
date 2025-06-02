package com.dmitrystonie.sunrisemoonriseapp.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dmitrystonie.sunrisemoonriseapp.presentation.MainScreenViewModel
import com.dmitrystonie.sunrisemoonriseapp.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class PlaceFragment : Fragment() {
    private val viewModel: MainScreenViewModel by activityViewModels<MainScreenViewModel>()

    private lateinit var mapView: MapView
    private lateinit var backButtonView: View
    private lateinit var selectButtonView: View
    private lateinit var placeNameView: TextView


    lateinit var inputListener: InputListener
    lateinit var placemarkTapListener: MapObjectTapListener
    lateinit var imageProvider: ImageProvider
    var selectedPlacemark: MapObject? = null

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
        placeNameView = view.findViewById(R.id.placeName)

        viewModel.placeInfo.observe(viewLifecycleOwner) { place ->
            Log.d("INFO", "$place")
            placeNameView.text = place.name
        }

        imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.marker)
        placemarkTapListener = MapObjectTapListener { placemark, point ->
            placemark.isVisible = false
            true
        }
        inputListener = object : InputListener {
            override fun onMapTap(
                p0: Map,
                p1: Point
            ) {
                val placemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = p1
                    setIcon(imageProvider)
                }
                placemark.addTapListener(placemarkTapListener)
                selectedPlacemark?.isVisible = false
                selectedPlacemark = placemark
                viewModel.getPlaceName(p1.latitude.toString(), p1.longitude.toString())
            }

            override fun onMapLongTap(
                p0: Map,
                p1: Point
            ) {
                val placemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = p1
                    setIcon(imageProvider)
                }
                placemark.addTapListener(placemarkTapListener)
                selectedPlacemark?.isVisible = false
                selectedPlacemark = placemark
            }
        }
        if (viewModel.placeInfo.value != null) {
            selectedPlacemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(
                    viewModel.placeInfo.value!!.latitude.toDouble(),
                    viewModel.placeInfo.value!!.longitude.toDouble()
                )
                setIcon(imageProvider)
            }
            selectedPlacemark!!.addTapListener(placemarkTapListener)
        }

        mapView.mapWindow.map.addInputListener(inputListener)
        selectButtonView.setOnClickListener {
            viewModel.savePlaceInfo()
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