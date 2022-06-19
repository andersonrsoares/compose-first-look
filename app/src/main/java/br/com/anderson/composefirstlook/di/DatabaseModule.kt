package br.com.anderson.composefirstlook.di

import android.content.Context
import androidx.room.Room
import br.com.anderson.composefirstlook.data.local.AppDatabase
import br.com.anderson.composefirstlook.data.local.CityWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): CityWeatherDao {
        return appDatabase.cityWeatherDao()
    }
}