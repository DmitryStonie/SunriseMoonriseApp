package com.example.sunrisemoonriseapp.di

import android.content.Context
import androidx.room.Room
import com.example.sunrisemoonriseapp.database.core.Database
import com.example.sunrisemoonriseapp.database.core.DayDao
import com.example.sunrisemoonriseapp.database.core.MoonDao
import com.example.sunrisemoonriseapp.database.core.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    fun provideDatabaseName(): String = "database"

    @Provides
    @Singleton
    fun provideDatabase(databaseName: String, @ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java,
            databaseName,
        ).build()

    @Provides
    fun provideDayDao(database: Database): DayDao = database.dayDao()

    @Provides
    fun provideMoonDao(database: Database): MoonDao = database.moonDao()

    @Provides
    fun providePlaceDao(database: Database): PlaceDao = database.placeDao()
}
