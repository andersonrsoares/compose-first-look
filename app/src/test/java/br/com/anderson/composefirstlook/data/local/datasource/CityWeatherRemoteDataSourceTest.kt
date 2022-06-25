package br.com.anderson.composefirstlook.data.local.datasource

import br.com.anderson.composefirstlook.ApiUtil
import br.com.anderson.composefirstlook.data.remote.datasource.RemoteDataSourceError
import br.com.anderson.composefirstlook.data.remote.datasource.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.datasource.CityWeatherRemoteDataSource
import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto
import br.com.anderson.composefirstlook.data.remote.network.WeatherService
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


class CityWeatherRemoteDataSourceTest {

    private val service = mock<WeatherService>()


    @Test
    fun `test success datasource response`() = runBlocking {
        val response = Response.success<WeatherContentDto>(
            ApiUtil.loadfile<WeatherContentDto>(
                "city_weather_response_success.json",
                WeatherContentDto::class.java
            )
        )
        given(service.findWeather(any())).willReturn(response)
        val datasource = CityWeatherRemoteDataSource(service)

        val dataSourceResponse = datasource.findWeatherByCity("foo")
        assert(
            dataSourceResponse is RemoteDataSourceResult.Success &&
                    dataSourceResponse.data.name == "Dublin"
        )
        assert(
            dataSourceResponse is RemoteDataSourceResult.Success &&
                    dataSourceResponse.data.weather.first().description == "clear sky"
        )
    }

    @Test
    fun `test notfound datasource response`() = runBlocking {
        val response = Response.error<WeatherContentDto>(
            404,
            ApiUtil.loadfile("city_weather_response_not_found.json").toResponseBody()
        )
        given(service.findWeather(any())).willReturn(response)
        val datasource = CityWeatherRemoteDataSource(service)

        val dataSourceResponse = datasource.findWeatherByCity("foo")
        assert(
            dataSourceResponse is RemoteDataSourceResult.Error &&
                    (dataSourceResponse.error as RemoteDataSourceError.NotFound).message == "city not found"
        )
    }

    @Test
    fun `test unauthorized datasource response`() = runBlocking {
        val response = Response.error<WeatherContentDto>(
            401,
            ApiUtil.loadfile("city_weather_response_unauthorized.json").toResponseBody()
        )
        given(service.findWeather(any())).willReturn(response)
        val datasource = CityWeatherRemoteDataSource(service)

        val dataSourceResponse = datasource.findWeatherByCity("foo")
        assert(
            dataSourceResponse is RemoteDataSourceResult.Error &&
                    (dataSourceResponse.error as RemoteDataSourceError.Unauthorized).message == "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."
        )
    }

    @Test
    fun `test unknown datasource response`() = runBlocking {
        val response = Response.error<WeatherContentDto>(500, "<>".toResponseBody())
        given(service.findWeather(any())).willReturn(response)
        val datasource = CityWeatherRemoteDataSource(service)

        val dataSourceResponse = datasource.findWeatherByCity("foo")
        assert(
            dataSourceResponse is RemoteDataSourceResult.Error &&
                    dataSourceResponse.error is RemoteDataSourceError.UnknownError
        )
    }

    @Test
    fun `test HttpException datasource response`() = runBlocking {
        val response = Response.error<WeatherContentDto>(500, "<>".toResponseBody())

        given(service.findWeather(any())).willThrow(HttpException(response))
        val datasource = CityWeatherRemoteDataSource(service)

        val dataSourceResponse = datasource.findWeatherByCity("foo")
        assert(
            dataSourceResponse is RemoteDataSourceResult.Error &&
                    dataSourceResponse.error is RemoteDataSourceError.UnknownError
        )
    }
}