package com.test.posts.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.posts.data.source.local.model.FavouriteLocalPost
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritePostDao {
    @Query("SELECT * FROM posts")
    fun getPostsFlow(): Flow<List<FavouriteLocalPost>>

    @Query("SELECT * FROM posts")
    suspend fun getPosts(): List<FavouriteLocalPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: FavouriteLocalPost)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()
}
