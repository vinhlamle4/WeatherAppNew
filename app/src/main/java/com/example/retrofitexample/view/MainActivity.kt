package com.example.retrofitexample.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.retrofitexample.BR
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.model.condition.*
import com.example.retrofitexample.viewmodel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            setVariable(BR.viewModel, mainViewModel)
            executePendingBindings()
        }

        initViewAction()
        setUpObserver()
    }

    private fun initViewAction() {
        binding.apply {
            btnGetApi.setOnClickListener {
                val cityName = edtCitySearch.text.toString()
                if (cityName.trim().isNotBlank()) {
                    textInputCity.error = null
                    mainViewModel.getLocationAPI(cityName)
                } else {
                    textInputCity.error = "This field is required"
                }
            }
        }
    }

    private fun setUpObserver() {
        mainViewModel.apply {
            requestFail.observe(this@MainActivity) { ErrorMessage ->
                if (!ErrorMessage.isNullOrBlank()) {
                    Snackbar.make(binding.constraintContainer, ErrorMessage, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}