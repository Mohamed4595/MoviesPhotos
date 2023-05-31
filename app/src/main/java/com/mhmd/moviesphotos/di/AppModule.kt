package com.mhmd.moviesphotos.di

import com.mhmd.core.util.Logger
import org.koin.dsl.module


val AppModule = module {

    single {
        Logger(
            tag = "AppDebug",
            isDebug = true
        )
    }
}