package com.mhmd.moviesphotos.di

import com.movieslist.data.cache.MoviesServiceLocalImpl
import com.movieslist.data.network.MoviesServiceRemoteImpl
import com.movieslist.domain.MoviesServiceLocal
import com.movieslist.domain.MoviesServiceRemote
import com.movieslist.interactors.GetMovies
import com.movieslist.presentation.ui.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MoviesModule = module {

    single<MoviesServiceRemote> { MoviesServiceRemoteImpl(get()) }
    single<MoviesServiceLocal> { MoviesServiceLocalImpl(get()) }

    single {
        GetMovies(
            get(),
            get()
        )
    }

    viewModel { MoviesListViewModel(get(), get()) }

}