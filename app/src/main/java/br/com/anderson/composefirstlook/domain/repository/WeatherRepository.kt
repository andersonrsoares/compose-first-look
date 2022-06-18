package br.com.anderson.composefirstlook.domain.repository


import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.datasource.IWeatherRemoteDataSource
import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.converter.TemperatureConverter
import br.com.anderson.composefirstlook.domain.model.Weather
import br.com.anderson.composefirstlook.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import br.com.anderson.composefirstlook.domain.model.toWeather

class WeatherRepository @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val weatherRemoteDataSource: IWeatherRemoteDataSource
) : IWeatherRepository {

    override fun fetchWeatherByCity(cityName: String): Flow<DataState<Weather>> {
        return flow {

            emit(DataState.Loading())

            emit(when(val result = weatherRemoteDataSource.findWeatherByCity(cityName)) {
                is RemoteDataSourceResult.Success -> DataState.Success(result.data.toWeather().apply {
                    this.temperature = TemperatureConverter.kelvinToCelsius(temperature)
                })
                is RemoteDataSourceResult.Error -> DataState.Failure("error")
            })

        }.flowOn(dispatcherProvider.io)
    }
}