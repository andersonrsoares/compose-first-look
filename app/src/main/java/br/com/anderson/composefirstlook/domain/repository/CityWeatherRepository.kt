package br.com.anderson.composefirstlook.domain.repository


import br.com.anderson.composefirstlook.data.local.datasource.ICityWeatherLocalDataSource
import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceError
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.datasource.ICityWeatherRemoteDataSource
import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.FailureReason
import br.com.anderson.composefirstlook.domain.converter.TemperatureConverter
import br.com.anderson.composefirstlook.domain.model.CityWeather
import br.com.anderson.composefirstlook.domain.model.toCityWeather
import br.com.anderson.composefirstlook.domain.model.toCityWeatherEntity
import br.com.anderson.composefirstlook.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityWeatherRepository @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val weatherRemoteDataSource: ICityWeatherRemoteDataSource,
    private val weatherLocalDataSource: ICityWeatherLocalDataSource
) : ICityWeatherRepository {

    override fun fetchWeatherByCity(cityName: String): Flow<DataState<CityWeather>> {
        return flow {

            emit(DataState.Loading())

            emit(when(val result = weatherRemoteDataSource.findWeatherByCity(cityName)) {
                is RemoteDataSourceResult.Success -> {
                    weatherLocalDataSource.addCityWeather(result.data.toCityWeatherEntity())
                    DataState.Success(result.data.toCityWeather().apply {
                        this.temperature = TemperatureConverter.kelvinToCelsius(temperature)
                    })
                }

                is RemoteDataSourceResult.Error -> DataState.Failure(when(result.error) {
                    is RemoteDataSourceError.NotFound -> FailureReason.ServerError(result.error.message)
                    is RemoteDataSourceError.Unauthorized, RemoteDataSourceError.NetworkError -> FailureReason.NetworkIssue()
                    else -> FailureReason.GenericError()
                })
            })

        }.flowOn(dispatcherProvider.io)
    }

    override fun fetchWeatherCityHistory(): Flow<DataState<List<CityWeather>>> {
        return flow {
            emit(DataState.Loading())
            emit(DataState.Success(weatherLocalDataSource.getLastFiveCitiesSearched().map {
                it.toCityWeather().apply {
                    this.temperature = TemperatureConverter.kelvinToCelsius(temperature)
                }
            }))
        }.flowOn(dispatcherProvider.io)
    }
}