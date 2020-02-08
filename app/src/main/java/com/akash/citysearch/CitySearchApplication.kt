package com.akash.citysearch

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CitySearchApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@CitySearchApplication)
            modules(listOf(dataSourceModule, viewmodelModule, networkStateModule))
        }
    }
}