package br.com.anderson.composefirstlook.di

import br.com.anderson.composefirstlook.data.remote.datasource.IWeatherRemoteDataSource
import br.com.anderson.composefirstlook.data.remote.datasource.WeatherRemoteDataSource
import br.com.anderson.composefirstlook.data.remote.network.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import br.com.anderson.composefirstlook.data.remote.network.WeatherService
import br.com.anderson.composefirstlook.domain.repository.IWeatherRepository
import br.com.anderson.composefirstlook.domain.repository.WeatherRepository
import br.com.anderson.composefirstlook.util.DispatcherProvider
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherModule {

    @Binds
    abstract fun provideWeatherRemoteDataSource(weatherRemoteDataSource: WeatherRemoteDataSource): IWeatherRemoteDataSource

    @Binds
    abstract fun provideWeatherRepository(weatherRepository: WeatherRepository): IWeatherRepository

}