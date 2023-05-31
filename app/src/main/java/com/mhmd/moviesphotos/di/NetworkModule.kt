package com.mhmd.moviesphotos.di

import com.movieslist.data.MoviesServiceImpl
import com.movieslist.domain.MoviesService
import com.movieslist.interactors.GetMovies
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val NetworkModule = module {
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

    }

}