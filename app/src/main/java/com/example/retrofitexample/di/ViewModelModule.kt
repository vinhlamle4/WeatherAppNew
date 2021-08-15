package com.example.retrofitexample.di

import com.example.retrofitexample.repository.IWeatherRepository
import com.example.retrofitexample.repository.WeatherRepository
import com.example.retrofitexample.service.IWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule{
    @Provides
    @ViewModelScoped
    fun providerWeatherRepository(weatherService: IWeatherService): IWeatherRepository {
        return WeatherRepository(weatherService)
    }
}