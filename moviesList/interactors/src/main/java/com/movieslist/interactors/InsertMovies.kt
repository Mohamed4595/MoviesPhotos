package com.movieslist.interactors

import com.mhmd.constants.NetworkConstants.PAGE_SIZE
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.UIComponent
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceLocal

class InsertMovies(
    private val serviceLocal: MoviesServiceLocal
) {

    suspend fun execute(
        page: Int, movies: List<Movie>,
        pageSize: Int = PAGE_SIZE,
        totalResultsRemote: Int? = null,
        totalPagesRemote: Int? = null
    ): DataState<Pagination<Movie>> =

        when (val insertResult = serviceLocal.insertLocalMovies(
            movies
        )) {
            is ApiResponse.Fail ->
                DataState.Error(
                    uiComponent = UIComponent.Dialog(
                        title = "Local Error",
                        description = insertResult.response.message.toString()
                    )

                )

            is ApiResponse.Success -> {
                val result = GetLocalMovies(serviceLocal).execute(
                    page = page,
                    pageSize,
                    totalResultsRemote,
                    totalPagesRemote
                )
                result
            }
        }
}








