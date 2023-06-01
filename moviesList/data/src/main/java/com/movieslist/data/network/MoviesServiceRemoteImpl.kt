package com.movieslist.data.network

import com.mhmd.constants.NetworkConstants
import com.mhmd.constants.NetworkPramsConstants
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.pathUrl
import com.mhmd.core.domain.toFailedResponse
import com.mhmd.core.domain.toPagination
import com.movieslist.data.network.EndPoints.MOVIE_SERVICES_REST
import com.movieslist.data.network.model.MovieResponseDto
import com.movieslist.data.network.model.toMovie
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesServiceRemote
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MoviesServiceRemoteImpl(private val client: HttpClient) : MoviesServiceRemote {

    override suspend fun getRemoteMovies(
        page: Int
    ): ApiResponse<Pagination<Movie>> {

        val httpResponse: HttpResponse = client.get {
            pathUrl(MOVIE_SERVICES_REST)
            parameter(NetworkPramsConstants.METHOD, "flickr.photos.search")
            parameter(NetworkPramsConstants.FORMAT, "json")
            parameter(NetworkPramsConstants.NO_JSON_CALLBACK, "50")
            parameter(NetworkPramsConstants.TEXT, "Color")
            parameter(NetworkPramsConstants.PAGE, page)
            parameter(NetworkPramsConstants.PER_PAGE, NetworkConstants.PAGE_SIZE)
            parameter(NetworkPramsConstants.API_KEY, NetworkConstants.API_KEY)
        }
        return if (httpResponse.status.value in 200..299) {
            val response: MovieResponseDto = httpResponse.receive()
            ApiResponse.Success(response.photos.toPagination { it.toMovie() })
        } else {
            //httpResponse.receive()
            val response = FailedResponseDto(
                statusMessage = "failed to call api",
                statusCode = httpResponse.status.value
            )
            ApiResponse.Fail(response.toFailedResponse())
        }

    }
}
