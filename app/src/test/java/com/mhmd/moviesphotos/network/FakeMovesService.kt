package com.mhmd.moviesphotos.network

import com.mhmd.constants.NetworkConstants
import com.mhmd.constants.NetworkConstants.BASE_URL
import com.mhmd.constants.NetworkPramsConstants
import com.mhmd.core.data.MoviesDataEmpty
import com.mhmd.core.data.MoviesDataMalformed
import com.mhmd.core.data.MoviesDataValid
import com.movieslist.data.network.EndPoints
import com.movieslist.data.network.MoviesServiceRemoteImpl
import com.movieslist.domain.MoviesServiceRemote
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.http.hostWithPort

class FakeMoviesService {

    companion object Factory {

        private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
        private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

        fun build(
            type: MoviesServiceResponseType
        ): MoviesServiceRemote {
            val client = HttpClient(MockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(
                        kotlinx.serialization.json.Json {
                            ignoreUnknownKeys =
                                true // if the server sends extra fields, ignore them
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                engine {
                    addHandler { request ->
                        when (request.url.fullUrl) {
                            "${BASE_URL}${EndPoints.MOVIE_SERVICES_REST}?" +
                                    "${NetworkPramsConstants.METHOD}=flickr.photos.search&" +
                                    "${NetworkPramsConstants.FORMAT}=json&" +
                                    "${NetworkPramsConstants.NO_JSON_CALLBACK}=50&" +
                                    "${NetworkPramsConstants.TEXT}=Color&" +
                                    "${NetworkPramsConstants.PAGE}=1&" +
                                    "${NetworkPramsConstants.PER_PAGE}=${NetworkConstants.PAGE_SIZE}&" +
                                    "${NetworkPramsConstants.API_KEY}=${NetworkConstants.API_KEY}" -> {
                                val responseHeaders = headersOf(
                                    "Content-Type" to listOf("application/json", "charset=utf-8")
                                )
                                when (type) {
                                    is MoviesServiceResponseType.EmptyList -> {
                                        respond(
                                            MoviesDataEmpty.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MoviesServiceResponseType.MalformedData -> {
                                        respond(
                                            MoviesDataMalformed.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MoviesServiceResponseType.GoodData -> {
                                        respond(
                                            MoviesDataValid.data,
                                            status = HttpStatusCode.OK,
                                            headers = responseHeaders
                                        )
                                    }

                                    is MoviesServiceResponseType.Http404 -> {
                                        respond(
                                            MoviesDataEmpty.data,
                                            status = HttpStatusCode.NotFound,
                                            headers = responseHeaders
                                        )
                                    }
                                }
                            }

                            else -> error("Unhandled ${request.url.fullUrl}")
                        }
                    }
                }
            }
            return MoviesServiceRemoteImpl(client)
        }
    }
}