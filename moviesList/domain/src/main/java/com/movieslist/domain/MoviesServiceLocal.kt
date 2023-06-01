package com.movieslist.domain

import com.mhmd.constants.NetworkConstants
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.Pagination

interface MoviesServiceLocal {
    suspend fun getLocalMovies(
        page: Int = 1,
        pageSize: Int = NetworkConstants.PAGE_SIZE,
        totalResultsRemote: Int? = null,
        totalPagesRemote: Int? = null
    ): ApiResponse<Pagination<Movie>>

    suspend fun deleteAllLocalMovies(): ApiResponse<Any>
    suspend fun insertLocalMovies(movies: List<Movie>): ApiResponse<Any>
}