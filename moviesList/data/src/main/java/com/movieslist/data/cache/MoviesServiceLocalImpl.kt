package com.movieslist.data.cache

import com.mhmd.constants.NetworkConstants.PAGE_SIZE
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.toFailedResponse
import com.movieslist.data.cache.model.toMovie
import com.movieslist.data.cache.model.toMovieEntity
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceLocal

class MoviesServiceLocalImpl(private val dao: MovieDao) : MoviesServiceLocal {

    override suspend fun getLocalMovies(
        page: Int, pageSize: Int, totalResultsRemote: Int?,
        totalPagesRemote: Int?
    ): ApiResponse<Pagination<Movie>> {
        return try {
            val totalResult = dao.getAllMoviesCount()
            val response = dao.getMovies(page = page,pageSize)
            val pagination = Pagination(
                page = page,
                totalResults = totalPagesRemote ?: totalResult,
                totalPages = totalPagesRemote ?: (totalResult / PAGE_SIZE),
                results = response.map { it.toMovie() }
            )
            ApiResponse.Success(pagination)
        } catch (e: Exception) {
            val response = FailedResponseDto(
                statusMessage = "failed to get data from local " + e.message.toString(),
                statusCode = 0
            )
            ApiResponse.Fail(response.toFailedResponse())
        }
    }

    override suspend fun deleteAllLocalMovies(): ApiResponse<Any> {
        return try {
            dao.deleteAllMovies()
            ApiResponse.Success()
        } catch (e: Exception) {
            val response = FailedResponseDto(
                statusMessage = "failed to delete data from local " + e.message.toString(),
                statusCode = 0
            )
            ApiResponse.Fail(response.toFailedResponse())
        }
    }

    override suspend fun insertLocalMovies(movies: List<Movie>): ApiResponse<Any> {
        return try {
            dao.insertMovies(movies = movies.map { it.toMovieEntity() })
            ApiResponse.Success()
        } catch (e: Exception) {
            val response = FailedResponseDto(
                statusMessage = "failed to insert data into local " + e.message.toString(),
                statusCode = 0
            )
            ApiResponse.Fail(response.toFailedResponse())
        }
    }
}
