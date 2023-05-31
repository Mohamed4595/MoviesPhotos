package com.movieslist.data.model

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
data class MovieDto(
    val id: String?,
    val owner: String?,
    val secret: String?,
    val server: String?,
    val farm: Int?,
    val title: String?,
    @SerialName("ispublic")
    val isPublic: Int?,
    @SerialName("isfriend")
    val isFriend: Int?,
    @SerialName("isfamily")
    val isFamily: Int?
)

fun MovieDto.toMovie(): Movie? {
    return if (id.isNullOrBlank() || secret.isNullOrBlank() || server.isNullOrBlank() || farm == null)
        null
    else Movie(
        photoUrl = getPhotoUrl(
            photoId = PhotoId(id),
            photoFarm = PhotoFarm(farm),
            photoSecret = PhotoSecret(secret),
            photoServer = PhotoServer(server)
        ),
        title = Title(title ?: ""),
        owner = Owner(owner ?: ""),
        isPublic = IsPublic(isPublic == 1),
        isFriend = IsFriend(isFriend == 1),
        isFamily = IsFamily(isFamily == 1)
    )
}

fun getPhotoUrl(
    photoId: PhotoId,
    photoFarm: PhotoFarm,
    photoSecret: PhotoSecret,
    photoServer: PhotoServer
): PhotoUrl {
    return PhotoUrl("https://farm${photoFarm.value}.static.flickr.com/${photoServer.value}/${photoId.value}_${photoSecret.value}.jpg")
}
