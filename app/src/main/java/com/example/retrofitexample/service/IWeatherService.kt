package com.example.retrofitexample.service

import com.example.retrofitexample.model.condition.Condition
import com.example.retrofitexample.model.location.WeatherLocation
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IWeatherService {
    @GET("locations/v1/cities/search")
    suspend fun getLocation(
        @Query("apikey") apiKey: String,
        @Query("q") location: String
    ): Response<ArrayList<WeatherLocation>>

    @GET("currentconditions/v1/{locationKey}")
    suspend fun getCondition(@Path("locationKey") locationKey: String, @Query("apikey") apiKey: String, @Query("details") details: Boolean): Response<ArrayList<Condition>>
}