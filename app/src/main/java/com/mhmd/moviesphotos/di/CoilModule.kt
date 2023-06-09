package com.mhmd.moviesphotos.di

import android.app.Application
import coil.ImageLoader
import coil.memory.MemoryCache
import org.koin.dsl.module

val CoilModule = module {

    /**
     * Coil docs say: Coil performs best when you create a single ImageLoader and share it throughout your app. This is because each ImageLoader has its own memory cache, bitmap pool, and network observer.
     * For testing: https://coil-kt.github.io/coil/image_loaders/#testing
     */

    factory { provideImageLoader(get()) }
}

fun provideImageLoader(app: Application): ImageLoader {
    return ImageLoader.Builder(app)
        .error(com.mhmd.components.R.drawable.ic_movie)
        .placeholder(com.mhmd.components.R.drawable.ic_movie)
        .memoryCache { MemoryCache.Builder(app.applicationContext).maxSizePercent(0.25).build() } // Don't know what is recommended?
        .crossfade(true)
        .crossfade(1000)
        .build()
}












