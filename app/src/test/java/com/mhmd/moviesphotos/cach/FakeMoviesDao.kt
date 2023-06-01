package com.mhmd.moviesphotos.cach

import com.movieslist.data.cache.MovieDao
import com.movieslist.data.cache.model.MovieEntity

class FakeMoviesDao : MovieDao {

    private val moviesList = mutableListOf<MovieEntity>(
        MovieEntity(
            id = "1",
            title = "title",
            owner = "owner",
            photoUrl = "photoUrl",
            isPublic = 0,
            isFamily = 0,
            isFriend = 0
        ),
        MovieEntity(
            id = "2",
            title = "title",
            owner = "owner",
            photoUrl = "photoUrl",
            isPublic = 0,
            isFamily = 0,
            isFriend = 0
        ),
        MovieEntity(
            id = "3",
            title = "title",
            owner = "owner",
            photoUrl = "photoUrl",
            isPublic = 0,
            isFamily = 0,
            isFriend = 0
        )
    )

    override suspend fun insertMovies(movies: List<MovieEntity>): LongArray {
        val moviesListIds = moviesList.map { it.id }
        movies.forEach { newMovie ->
            if (moviesListIds.contains(newMovie.id)) {
                moviesList.add(moviesListIds.indexOf(newMovie.id), newMovie)
            } else moviesList.add(newMovie)

        }
        return arrayOf(1L).toLongArray()
    }

    override suspend fun deleteAllMovies() {
        moviesList.clear()
    }

    override suspend fun getAllMoviesCount(): Int {
        return moviesList.size
    }

    override suspend fun getMovies(page: Int, pageSize: Int): List<MovieEntity> {
        return if ((page - 1) * pageSize < moviesList.size && page * pageSize < moviesList.size)
            moviesList.subList((page - 1) * pageSize, page * pageSize)
        else moviesList
    }
}