package com.mhmd.moviesphotos.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movieslist.data.cache.MovieDao
import com.movieslist.data.cache.model.MovieEntity


@Database(entities = [MovieEntity::class ], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object{
        const val DATABASE_NAME: String = "movies_db"
    }

}