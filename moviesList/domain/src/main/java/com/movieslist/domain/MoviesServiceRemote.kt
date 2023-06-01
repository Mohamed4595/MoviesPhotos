package com.movieslist.domain

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.Pagination

interface MoviesServiceRemote {
    suspend fun getRemoteMovies(page: Int = 1): ApiResponse<Pagination<Movie>>
}