package com.mhmd.moviesphotos.cach

import com.google.common.truth.Truth.assertThat
import com.mhmd.core.domain.DataState
import com.movieslist.data.cache.MoviesServiceLocalImpl
import com.movieslist.data.cache.model.MovieEntity
import com.movieslist.data.cache.model.toMovie
import com.movieslist.interactors.DeleteMovies
import com.movieslist.interactors.GetLocalMovies
import com.movieslist.interactors.InsertMovies
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalMoviesTest {
    private lateinit var getLocalMovies: GetLocalMovies
    private lateinit var insertMovies: InsertMovies
    private lateinit var deleteMovies: DeleteMovies

    private lateinit var fakeMoviesDao: FakeMoviesDao

    @Before
    fun setup() {
        fakeMoviesDao = FakeMoviesDao()
    }

    @Test
    fun `Insert New Movies`() = runBlocking {
        val service = MoviesServiceLocalImpl(fakeMoviesDao)
        insertMovies = InsertMovies(service)

        val moviesList = mutableListOf<MovieEntity>(
            MovieEntity(
                id = "4",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            ),
            MovieEntity(
                id = "5",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            ),
            MovieEntity(
                id = "6",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            )
        )

        // Execute the use-case
        val emissions =
            insertMovies.execute(page = 1, pageSize = 5, movies = moviesList.map { it.toMovie() })

        // Confirm second emission is data
        assertThat(emissions is DataState.Success).isTrue()
        assertThat((emissions as DataState.Success).data?.results?.size == 5).isTrue()

    }

    @Test
    fun `Insert Existing Movies`() = runBlocking {
        val service = MoviesServiceLocalImpl(fakeMoviesDao)
        insertMovies = InsertMovies(service)

        val moviesList = mutableListOf<MovieEntity>(
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
            )
        )

        // Execute the use-case
        val emissions =
            insertMovies.execute(page = 1, pageSize = 3, movies = moviesList.map { it.toMovie() })

        // Confirm second emission is data
        assertThat(emissions is DataState.Success).isTrue()
        assertThat((emissions as DataState.Success).data?.results?.size == 3).isTrue()

    }

    @Test
    fun `Get all Movies`() = runBlocking {

        val service = MoviesServiceLocalImpl(fakeMoviesDao)
        getLocalMovies = GetLocalMovies(service)

        // Execute the use-case
        val emissions =
            getLocalMovies.execute(page = 1, pageSize = 2)

        // Confirm second emission is data
        assertThat(emissions is DataState.Success).isTrue()
        assertThat((emissions as DataState.Success).data?.results?.size == 2).isTrue()
    }

    @Test
    fun `Get all Movies with large page size`() = runBlocking {

        val service = MoviesServiceLocalImpl(fakeMoviesDao)
        getLocalMovies = GetLocalMovies(service)

        // Execute the use-case
        val emissions =
            getLocalMovies.execute(page = 1, pageSize = 10)

        // Confirm second emission is data
        assertThat(emissions is DataState.Success).isTrue()
        assertThat((emissions as DataState.Success).data?.results?.size == 3).isTrue()
    }

    @Test
    fun `Delete All Movies`() = runBlocking {
        val service = MoviesServiceLocalImpl(fakeMoviesDao)
        deleteMovies = DeleteMovies(service)
        val moviesList = mutableListOf<MovieEntity>(
            MovieEntity(
                id = "4",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            ),
            MovieEntity(
                id = "5",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            ),
            MovieEntity(
                id = "6",
                title = "title",
                owner = "owner",
                photoUrl = "photoUrl",
                isPublic = 0,
                isFamily = 0,
                isFriend = 0
            )
        )

        // Execute the use-case
        val emissions =
            deleteMovies.execute(page = 1, pageSize = 6, movies = moviesList.map { it.toMovie() })

        // Confirm second emission is data
        assertThat(emissions is DataState.Success).isTrue()
        assertThat((emissions as DataState.Success).data?.results?.size == 3).isTrue()
    }

}