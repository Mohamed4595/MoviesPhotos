package com.movieslist.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.core.domain.UIComponent
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovies(private val service: MoviesService) {

    fun execute(page: Int): Flow<DataState<Pagination<Movie>>> =
        flow {

            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            try {
                when (val result = service.getMovies(page)) {
                    is ApiResponse.Fail -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(
                            DataState.Error(
                                uiComponent = UIComponent.Dialog(
                                    title = "Network Data Error",
                                    description = result.response.message.toString()
                                )
                            )
                        )
                    }

                    is ApiResponse.Success -> {
                        emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                        emit(DataState.Success(result.data))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // log to crashlytics?
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
                emit(
                    DataState.Error(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown error"
                        )
                    )
                )
            }
        }
}




