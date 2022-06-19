package br.com.anderson.composefirstlook.domain.repository

import br.com.anderson.composefirstlook.ApiUtil
import br.com.anderson.composefirstlook.data.local.datasource.CityWeatherLocalDataSource
import br.com.anderson.composefirstlook.data.local.datasource.ICityWeatherLocalDataSource
import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity
import br.com.anderson.composefirstlook.data.remote.datasource.RemoteDataSourceError
import br.com.anderson.composefirstlook.data.remote.datasource.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.datasource.CityWeatherRemoteDataSource
import br.com.anderson.composefirstlook.data.remote.datasource.ICityWeatherRemoteDataSource
import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto
import br.com.anderson.composefirstlook.data.remote.network.WeatherService
import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.FailureReason
import br.com.anderson.composefirstlook.domain.converter.ITemperatureConverter
import br.com.anderson.composefirstlook.domain.converter.TemperatureConverter
import br.com.anderson.composefirstlook.util.DispatcherProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


class CityWeatherRepositoryTest {

    private val remoteDataSource = mock<ICityWeatherRemoteDataSource>()
    private val localDataSource = mock<ICityWeatherLocalDataSource>()
    private val temperatureConverter = mock<ITemperatureConverter>()

    private val dispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = testDispatcher
        override val io: CoroutineDispatcher
            get() = testDispatcher
        override val default: CoroutineDispatcher
            get() = testDispatcher
        override val unconfined: CoroutineDispatcher
            get() = testDispatcher
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val testScope = TestScope(testDispatcher)


    @Before
    fun start() {
        given(temperatureConverter.convert(any())).willReturn(28.0)
    }

    @Test
    fun `test success search city`() = testScope.runTest {

        val weatherContentDto = ApiUtil.loadfile(
            "city_weather_response_success.json",
            WeatherContentDto::class.java
        )
        given(remoteDataSource.findWeatherByCity(any())).willReturn(RemoteDataSourceResult.Success(
            weatherContentDto
        ))

        val datasource = CityWeatherRepository(dispatcherProvider,
            remoteDataSource,
            localDataSource,
            temperatureConverter)

       val flow =  datasource.fetchWeatherByCity("foo")


       assert(flow.first() is DataState.Loading)

        val successItem = flow.last()
       assert(successItem is DataState.Success && successItem.data.city == "Dublin")

       verify(localDataSource, times(1)).addCityWeather(any())
    }

    @Test
    fun `test failure search city`() = testScope.runTest {

        given(remoteDataSource.findWeatherByCity(any())).willReturn(RemoteDataSourceResult.Error(
            RemoteDataSourceError.NotFound("error")
        ))

        val datasource = CityWeatherRepository(dispatcherProvider,
            remoteDataSource,
            localDataSource,
            temperatureConverter)

        val flow =  datasource.fetchWeatherByCity("foo")


        assert(flow.first() is DataState.Loading)

        val last = flow.last()
        assert(last is DataState.Failure && last.reason is  FailureReason.ServerError)

        verify(localDataSource, never()).addCityWeather(any())
    }


    @Test
    fun `test success history`() = testScope.runTest {

        given(localDataSource.getLastFiveCitiesSearched())
            .willReturn(arrayListOf(CityWeatherEntity(id = 1, city = "Porto" )))

        val datasource = CityWeatherRepository(dispatcherProvider,
            remoteDataSource,
            localDataSource,
            temperatureConverter)

        val flow =  datasource.fetchWeatherCityHistory()

        assert(flow.first() is DataState.Loading)

        val successItem = flow.last()
        assert(successItem is DataState.Success && successItem.data.first().city == "Porto")

    }
}