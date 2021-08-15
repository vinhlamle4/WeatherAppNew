package com.example.retrofitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.model.condition.Condition
import com.example.retrofitexample.model.location.WeatherLocation
import com.example.retrofitexample.repository.IWeatherRepository
import com.example.retrofitexample.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: IWeatherRepository
) : ViewModel() {

    private val _location = MutableLiveData<WeatherLocation>()
    val location: LiveData<WeatherLocation> get() = _location

    private val _condition = MutableLiveData<Condition>()
    val condition: LiveData<Condition> get() = _condition

    private val _requestFail = MutableLiveData<String>()
    val requestFail: LiveData<String> get() = _requestFail

    private val _isShowLoading = MutableLiveData(false)
    val isShowLoading: LiveData<Boolean> get() = _isShowLoading

    fun getLocationAPI(cityName: String) {
        viewModelScope.launch {
            showLoading()
            weatherRepository.fetchLocation(cityName,
                onSuccess = {
                    _location.postValue(it)
                    fetchCondition(it.Key)
                }, onFailed = {
                    _requestFail.postValue(it)
                    hideLoading()
                })
        }
    }

    private fun fetchCondition(locationKey: String) {
        viewModelScope.launch {
            weatherRepository.fetchCondition(locationKey, onSuccess = {
                hideLoading()
                _condition.postValue(it)
            }, onFailed = {
                _requestFail.postValue(it)
                hideLoading()
            })
        }
    }

    private fun showLoading() {
        _isShowLoading.postValue(true)
    }

    private fun hideLoading() {
        _isShowLoading.postValue(false)
    }
}