package com.example.sunrisemoonriseapp.repository

import com.example.sunrisemoonriseapp.database.datasources.LocalPlaceDataSource
import com.example.sunrisemoonriseapp.entities.Place
import com.example.sunrisemoonriseapp.retrofit.geocoder.FeatureMember
import com.example.sunrisemoonriseapp.retrofit.geocoder.GeocoderRemoteData
import com.example.sunrisemoonriseapp.retrofit.geocoder.GeocoderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val localPlaceDateSource: LocalPlaceDataSource,
    private val geocoderRemoteData: GeocoderRemoteData
) {
    suspend fun getLocalPlace(): Place? {
        return withContext(Dispatchers.IO) {
            var place: Place? = null
            launch {
                place = localPlaceDateSource.getPlaceById(0)
            }.join()
            return@withContext place
        }
    }

    suspend fun getPlaceName(place: Place): String?{
        return withContext(Dispatchers.IO) {
            val response = geocoderRemoteData.getPlace(place.latitude, place.longitude)
            if(response.isSuccessful){
                val places = response.body()?.response?.geoObjectCollection?.featureMember
                if(places == null || places.isEmpty()){
                    return@withContext null
                } else{
                    return@withContext getName(places)
                }
            } else{
                return@withContext null
            }
        }
    }

    suspend fun updatePlace(place: Place) {
        return withContext(Dispatchers.IO) {
            localPlaceDateSource.addPlace(
                place
            )
        }
    }

    private fun getName(places: List<FeatureMember>): String{
        val kindNames = HashMap<String, String>()
        kindPriority.forEach { kind -> kindNames[kind] = "" }
        for(place in places){
            val kind = place.geoObject.metaDataProperty.geocoderMetaData.kind
            if(kind in kindPriority){
                kindNames[kind] = place.geoObject.name
            }
        }
        for(kind in kindPriority){
            if(kindNames[kind] != ""){
                return kindNames[kind]!!
            }
        }
        return places[0].geoObject.name
    }

    companion object{
        private val kindPriority = listOf("locality", "district", "area", "province", "country", "other")
    }

}