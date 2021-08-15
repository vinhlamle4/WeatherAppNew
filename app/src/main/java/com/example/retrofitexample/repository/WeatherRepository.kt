package com.example.retrofitexample.repository

import android.util.Log
import com.example.retrofitexample.BuildConfig
import com.example.retrofitexample.model.condition.Condition
import com.example.retrofitexample.model.location.WeatherLocation
import com.example.retrofitexample.service.IWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val IWeatherService: IWeatherService
) : IWeatherRepository {

    override suspend fun fetchLocation(
        location: String,
        onSuccess: (location: WeatherLocation) -> Unit,
        onFailed: (message: String) -> Unit
    ) = withContext(Dispatchers.IO) {

        try {
            val response = IWeatherService.getLocation(BuildConfig.API_KEY, location)
            when (response.isSuccessful) {
                true -> {
                    val locations = response.body()
                    if (locations != null && locations.isNotEmpty()) {
                        onSuccess(locations.first())
                    }
                }
                false -> {
                    Log.e("RetrofitCallFailed", response.message())
                    onFailed(response.message() ?: "Get data fail, please try again")
                }
            }
        } catch(ex: Exception) {
            if(ex is UnknownHostException) {
                onFailed("No network connection, please check your net work.")
            } else {
                onFailed("Can't connect to server, please try again later")
            }
        }
    }

    override suspend fun fetchCondition(
        locationKey: String,
        onSuccess: (condition: Condition) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        try {
            val response = IWeatherService.getCondition(locationKey, BuildConfig.API_KEY, details = true)
            when (response.isSuccessful) {
                true -> {
                    val condition = response.body()
                    if (condition != null && condition.isNotEmpty()) {
                        onSuccess(condition.first())
                    }
                }
                false -> {
                    Log.e("RetrofitCallFailed", response.message())
                    onFailed(response.message() ?: "Get data fail, please try again")
                }
            }
        } catch(ex: Exception) {
            if(ex is UnknownHostException) {
                onFailed("No network connection, please check your net work.")
            } else {
                onFailed("Can't connect to server, please try again later")
            }
        }
    }
}