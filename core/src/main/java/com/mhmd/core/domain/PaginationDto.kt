package com.mhmd.core.domain

import com.mhmd.core.domain.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto<T>(
    @SerialName("total")
    val totalResults: Int?,
    val page: Int?,
    @SerialName("pages")
    val totalPages: Int?,
    @SerialName("photo")
    val results: List<T>?
)
fun <T, R>  PaginationDto<T>.toPagination(transform: (T) -> R?): Pagination<R> {
    return Pagination(
        totalPages = totalPages,
        totalResults = totalResults,
        results = results?.mapNotNull { transform(it) },
        page = page
    )
}