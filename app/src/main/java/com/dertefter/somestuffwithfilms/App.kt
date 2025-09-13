package com.dertefter.somestuffwithfilms

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import com.dertefter.somestuffwithfilms.di.KoinModules.networkModule
import com.dertefter.somestuffwithfilms.di.KoinModules.repositoryModule
import com.dertefter.somestuffwithfilms.di.KoinModules.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}