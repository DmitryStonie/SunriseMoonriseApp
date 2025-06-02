package com.dmitrystonie.sunrisemoonriseapp.data.repositories

import com.dmitrystonie.sunrisemoonriseapp.data.datasources.LocalPlaceDataSource
import com.dmitrystonie.sunrisemoonriseapp.domain.repositories.PlaceRepository
import com.dmitrystonie.sunrisemoonriseapp.domain.entities.Place
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder.FeatureMember
import com.dmitrystonie.sunrisemoonriseapp.data.retrofit.geocoder.GeocoderRemoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepositoryImpl @Inject constructor(
    private val localPlaceDateSource: LocalPlaceDataSource,
    private val geocoderRemoteData: GeocoderRemoteData
): PlaceRepository {
    override suspend fun getLocalPlace(): Place? {
        return withContext(Dispatchers.IO) {
            var place: Place? = null
            launch {
                place = localPlaceDateSource.getPlaceById(0)
            }.join()
            return@withContext place
        }
    }

    override suspend fun getPlaceName(place: Place): String?{
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

    override suspend fun updatePlace(place: Place) {
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