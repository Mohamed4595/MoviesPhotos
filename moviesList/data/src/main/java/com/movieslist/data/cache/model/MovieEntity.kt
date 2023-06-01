package com.movieslist.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.movieslist.domain.IsFamily
import com.movieslist.domain.IsFriend
import com.movieslist.domain.IsPublic
import com.movieslist.domain.Movie
import com.movieslist.domain.Owner
import com.movieslist.domain.PhotoId
import com.movieslist.domain.PhotoUrl
import com.movieslist.domain.Title

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "photo_url")
    var photoUrl: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "owner")
    var owner: String,

    @ColumnInfo(name = "is_public")
    var isPublic: Int,

    @ColumnInfo(name = "is_friend")
    var isFriend: Int,

    @ColumnInfo(name = "is_family")
    var isFamily: Int
)

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = photoId.value,
        photoUrl = photoUrl.value,
        owner = owner.value,
        title = title.value,
        isPublic = if (isPublic.value) 1 else 0,
        isFamily = if (isFamily.value) 1 else 0,
        isFriend = if (isFriend.value) 1 else 0,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        photoId = PhotoId(id),
        photoUrl = PhotoUrl(photoUrl),
        title = Title(title),
        owner = Owner(owner),
        isPublic = IsPublic(isPublic == 1),
        isFriend = IsFriend(isFriend == 1),
        isFamily = IsFamily(isFamily == 1)
    )
}