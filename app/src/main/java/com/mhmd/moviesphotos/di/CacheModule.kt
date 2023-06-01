package com.mhmd.moviesphotos.di

import android.app.Application
import androidx.room.Room
import com.mhmd.moviesphotos.appDatabase.AppDatabase
import com.movieslist.data.cache.MovieDao
import org.koin.dsl.module


val CacheModule = module {

    single {
        provideDb(get())
    }
    single {
        provideMovieDao(get())
    }
}

fun provideDb(app: Application): AppDatabase {
    return Room
        .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideMovieDao(db: AppDatabase): MovieDao {
    return db.movieDao()
}





