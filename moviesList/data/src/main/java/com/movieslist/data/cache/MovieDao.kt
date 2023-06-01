package com.movieslist.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhmd.constants.NetworkConstants.PAGE_SIZE
import com.movieslist.data.cache.model.MovieEntity


@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT COUNT(id) FROM movies")
    suspend fun getAllMoviesCount(): Int

    /**
     * Retrieve movies for a particular page.
     * Ex: page = 2 retrieves movies from 20-40.
     * Ex: page = 3 retrieves movies from 40-60
     */
    @Query(
        """
        SELECT * FROM movies 
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """
    )
    suspend fun getMovies(
        page: Int,
        pageSize: Int = PAGE_SIZE
    ): List<MovieEntity>
}