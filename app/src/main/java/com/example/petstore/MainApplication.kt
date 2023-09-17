package com.example.petstore

import android.app.Application
import com.example.petstore.di.databaseModule
import com.example.petstore.di.viewModelDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(databaseModule, viewModelDi)
        }
    }
}