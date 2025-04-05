package com.test.posts.data.source.local

import com.test.posts.data.source.local.model.FavouriteLocalPost
import kotlinx.coroutines.flow.Flow

interface FavouritePostLocalDataSource {

    fun getPosts(): Flow<List<FavouriteLocalPost>>

    suspend fun insert(post: FavouriteLocalPost)

    suspend fun deleteById(id: Long)

    suspend fun deleteAll()
}