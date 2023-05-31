package com.movieslist.domain

import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.Pagination

interface MoviesService {

    suspend fun getMovies(page: Int = 1): ApiResponse<Pagination<Movie>>

}