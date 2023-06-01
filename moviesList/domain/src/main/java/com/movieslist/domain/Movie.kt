package com.movieslist.domain

@JvmInline
value class PhotoId(val value: String)

@JvmInline
value class Owner(val value: String)

@JvmInline
value class PhotoSecret(val value: String)

@JvmInline
value class PhotoServer(val value: String)

@JvmInline
value class PhotoFarm(val value: Int)

@JvmInline
value class Title(val value: String)

@JvmInline
value class PhotoUrl(val value: String)

@JvmInline
value class IsPublic(val value: Boolean)

@JvmInline
value class IsFriend(val value: Boolean)

@JvmInline
value class IsFamily(val value: Boolean)


data class Movie(
    val photoId: PhotoId,
    val owner: Owner,
    val title: Title,
    val photoUrl: PhotoUrl,
    val isPublic: IsPublic,
    val isFriend: IsFriend,
    val isFamily: IsFamily
)













