package com.movieslist.data

import com.mhmd.constants.NetworkConstants
import com.mhmd.constants.NetworkPramsConstants
import com.mhmd.core.domain.ApiResponse
import com.mhmd.core.domain.FailedResponseDto
import com.mhmd.core.domain.Pagination
import com.mhmd.core.domain.pathUrl
import com.mhmd.core.domain.toFailedResponse
import com.mhmd.core.domain.toPagination
import com.movieslist.data.EndPoints.MOVIE_SERVICES_REST
import com.movieslist.data.model.MovieResponseDto
import com.movieslist.data.model.toMovie
import com.movieslist.domain.Movie
import com.movieslist.domain.MoviesService
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class MoviesServiceImpl(private val client: HttpClient) : MoviesService {

    override suspend fun getMovies(
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
