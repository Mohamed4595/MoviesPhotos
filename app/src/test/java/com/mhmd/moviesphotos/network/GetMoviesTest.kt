package com.mhmd.moviesphotos.network

import com.google.common.truth.Truth.assertThat
import com.mhmd.core.domain.DataState
import com.mhmd.core.domain.ProgressBarState
import com.mhmd.moviesphotos.cach.FakeMoviesDao
import com.movieslist.data.cache.MoviesServiceLocalImpl
import com.movieslist.domain.Movie
import com.movieslist.interactors.GetMovies
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMoviesTest {
    // system in test
    private lateinit var getMovies: GetMovies
    private lateinit var fakeMoviesDao: FakeMoviesDao

    @Before
    fun setup() {
        fakeMoviesDao = FakeMoviesDao()
    }

    @Test
    fun `Get Movies From Network with Good Data`() = runBlocking {
        // setup
        val remoteMoviesService = FakeMoviesService.build(
            type = MoviesServiceResponseType.GoodData // good data
        )
        val localMoviesService = MoviesServiceLocalImpl(fakeMoviesDao)


        getMovies = GetMovies(
            serviceRemote = remoteMoviesService,
            serviceLocal = localMoviesService
        )

        // Execute the use-case
        val emissions = getMovies.execute(page = 1).toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<Movie>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<Movie>>(ProgressBarState.Idle))

        // Confirm second emission is data
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.results?.size == 20).isTrue()

    }

    @Test
    fun `Get Movies From Network with Empty Data`() = runBlocking {
        // setup
        val remoteMoviesService = FakeMoviesService.build(
            type = MoviesServiceResponseType.EmptyList // good data
        )
        val localMoviesService = MoviesServiceLocalImpl(fakeMoviesDao)


        getMovies = GetMovies(
            serviceRemote = remoteMoviesService,
            serviceLocal = localMoviesService
        )

        // Execute the use-case
        val emissions = getMovies.execute(page = 1).toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<Movie>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<Movie>>(ProgressBarState.Idle))

        // Confirm second emission is data
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.results?.isEmpty()).isTrue()

    }

    @Test
    fun `Get Movies From Network with Malformed Data`() = runBlocking {
        // setup
        val remoteMoviesService = FakeMoviesService.build(
            type = MoviesServiceResponseType.MalformedData // good data
        )
        val localMoviesService = MoviesServiceLocalImpl(fakeMoviesDao)


        getMovies = GetMovies(
            serviceRemote = remoteMoviesService,
            serviceLocal = localMoviesService
        )

        // Execute the use-case
        val emissions = getMovies.execute(page = 1).toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<Movie>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<Movie>>(ProgressBarState.Idle))

        // Confirm second emission is error response
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.results?.size == 3).isTrue()
    }

    @Test
    fun `Get Movies From Network with Error Http`() = runBlocking {
        // setup
        val remoteMoviesService = FakeMoviesService.build(
            type = MoviesServiceResponseType.Http404 // good data
        )
        val localMoviesService = MoviesServiceLocalImpl(fakeMoviesDao)


        getMovies = GetMovies(
            serviceRemote = remoteMoviesService,
            serviceLocal = localMoviesService
        )

        // Execute the use-case
        val emissions = getMovies.execute(page = 1).toList()

        // First emission should be loading
        assertThat(emissions[0] == DataState.Loading<List<Movie>>(ProgressBarState.Loading)).isTrue()

        // Confirm loading state is IDLE
        assert(emissions[1] == DataState.Loading<List<Movie>>(ProgressBarState.Idle))

        // Confirm second emission is error response
        assertThat(emissions[2] is DataState.Success).isTrue()
        assertThat((emissions[2] as DataState.Success).data?.results?.size == 3).isTrue()
    }

}