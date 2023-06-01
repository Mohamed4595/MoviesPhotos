package com.movieslist.interactors

import com.mhmd.constants.NetworkConstants
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.UIComponent
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceLocal

class GetLocalMovies(
    private val serviceLocal: MoviesServiceLocal
) {

    suspend fun execute(
        page: Int,
        pageSize: Int = NetworkConstants.PAGE_SIZE,
        totalResultsRemote: Int? = null,
        totalPagesRemote: Int? = null
    ): DataState<Pagination<Movie>> =

        when (val getResult =
            serviceLocal.getLocalMovies(page, pageSize,totalResultsRemote, totalPagesRemote)) {
            is ApiResponse.Fail ->
                DataState.Error(
                    uiComponent = UIComponent.Dialog(
                        title = "Local Error",
                        description = getResult.response.message.toString()
                    )

                )

            is ApiResponse.Success ->
                DataState.Success(getResult.data)

        }

}







