package com.mhmd.moviesphotos.network

sealed class MoviesServiceResponseType{

    object EmptyList: MoviesServiceResponseType()

    object MalformedData: MoviesServiceResponseType()

    object GoodData: MoviesServiceResponseType()

    object Http404: MoviesServiceResponseType()
}
