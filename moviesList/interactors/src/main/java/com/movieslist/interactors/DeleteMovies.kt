package com.movieslist.interactors

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.UIComponent
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceLocal

class DeleteMovies(
    private val serviceLocal: MoviesServiceLocal
) {

    suspend fun execute(
        page: Int, movies: List<Movie>,
        totalResultsRemote: Int? = null,
        totalPagesRemote: Int? = null
    ): DataState<Pagination<Movie>> =
        when (val deleteResult = serviceLocal.deleteAllLocalMovies()) {
            is ApiResponse.Fail ->
                DataState.Error(
                    uiComponent = UIComponent.Dialog(
                        title = "Local Error",
                        description = deleteResult.response.message.toString()
                    )
                )


            is ApiResponse.Success ->
                InsertMovies(serviceLocal).execute(
                    page = page,
                    movies = movies,
                    totalResultsRemote = totalResultsRemote,
                    totalPagesRemote = totalPagesRemote
                )
        }
}








