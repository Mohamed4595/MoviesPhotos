package com.movieslist.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.ProgressBarState
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceLocal
import com.movieslist.domain.MoviesServiceRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovies(
    private val serviceRemote: MoviesServiceRemote,
    private val serviceLocal: MoviesServiceLocal
) {

    fun execute(page: Int): Flow<DataState<Pagination<Movie>>> =
        flow {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            try {
                when (val result = serviceRemote.getRemoteMovies(page)) {
                    is ApiResponse.Fail -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(GetLocalMovies(serviceLocal).execute(page))
                    }

                    is ApiResponse.Success -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        if (page > 1)
                            emit(
                                InsertMovies(serviceLocal).execute(
                                    page = page,
                                    movies = result.data?.results ?: emptyList(),
                                    totalResultsRemote = result.data?.totalResults,
                                    totalPagesRemote = result.data?.totalPages
                                )
                            )
                        else
                            emit(
                                DeleteMovies(serviceLocal).execute(
                                    page = page,
                                    movies = result.data?.results ?: emptyList(),
                                    totalResultsRemote = result.data?.totalResults,
                                    totalPagesRemote = result.data?.totalPages
                                )
                            )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                emit(GetLocalMovies(serviceLocal).execute(page))
            }
        }
}




