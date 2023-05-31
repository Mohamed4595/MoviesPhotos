package com.movieslist.presentation.ui

import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.Queue
import com.mhmd.core.domain.UIComponent
import com.movieslist.domain.Movie


data class MoviesListState(
    val movies: List<Movie> = listOf(),
    val isLoadingNextPage: ProgressBarState = ProgressBarState.Idle,
    val page: Int = 1,
    val totalPages: Int = 0,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
