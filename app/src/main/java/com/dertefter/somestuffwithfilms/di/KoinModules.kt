package com.dertefter.somestuffwithfilms.di

import com.dertefter.somestuffwithfilms.data.network.Api
import com.dertefter.somestuffwithfilms.data.repository.FilmsRepository
import com.dertefter.somestuffwithfilms.fragments.films.FilmsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KoinModules {
    val networkModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://s3-eu-west-1.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

    val repositoryModule = module {
        single { FilmsRepository(get()) }
    }

    val viewModelModule = module {
        viewModel { FilmsViewModel(get()) }
    }
}