package com.movieslist.presentation.ui

import com.movieslist.domain.PhotoUrl


sealed class MoviesListEvents {

    object GetMovies : MoviesListEvents()
    object GetNextPageMovies : MoviesListEvents()
    object OnRemoveHeadFromQueue : MoviesListEvents()
}
