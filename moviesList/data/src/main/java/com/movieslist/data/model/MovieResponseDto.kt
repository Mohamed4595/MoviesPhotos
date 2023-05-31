package com.movieslist.data.model

import com.mhmd.core.domain.PaginationDto
import com.movieslist.domain.IsFamily
import com.movieslist.domain.IsFriend
import com.movieslist.domain.IsPublic
import com.movieslist.domain.Movie
import com.movieslist.domain.Owner
import com.movieslist.domain.PhotoFarm
import com.movieslist.domain.PhotoId
import com.movieslist.domain.PhotoSecret
import com.movieslist.domain.PhotoServer
import com.movieslist.domain.PhotoUrl
import com.movieslist.domain.Title
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    val photos: PaginationDto<MovieDto>,
    val stat: String?
)
