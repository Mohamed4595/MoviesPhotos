package com.mhmd.moviesphotos.di

import com.movieslist.data.MoviesServiceImpl
import com.movieslist.domain.MoviesService
import com.movieslist.interactors.GetMovies
import com.movieslist.presentation.ui.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MoviesModule = module {
    single<MoviesService> { MoviesServiceImpl(get()) }
    single {
        GetMovies(
            get()
        )
    }

    viewModel { MoviesListViewModel(get(), get()) }

}