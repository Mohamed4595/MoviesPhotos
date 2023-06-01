package com.mhmd.moviesphotos.ui

import android.app.Application
import com.mhmd.moviesphotos.di.AppModule
import com.mhmd.moviesphotos.di.CacheModule
import com.mhmd.moviesphotos.di.CoilModule
import com.mhmd.moviesphotos.di.MoviesModule
import com.mhmd.moviesphotos.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(AppModule, CoilModule, NetworkModule, CacheModule, MoviesModule)
            )
        }
    }
}