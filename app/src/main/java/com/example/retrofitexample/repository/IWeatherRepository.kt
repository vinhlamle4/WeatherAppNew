package com.example.retrofitexample.repository

import com.example.retrofitexample.model.condition.Condition
import com.example.retrofitexample.model.location.WeatherLocation

interface IWeatherRepository {

    suspend fun fetchLocation(
        location: String,
        onSuccess: (location: WeatherLocation) -> Unit,
        onFailed: (message: String) -> Unit
    )

    suspend fun fetchCondition(
        locationKey: String,
        onSuccess: (condition: Condition) -> Unit,
        onFailed: (message: String) -> Unit
    )

}