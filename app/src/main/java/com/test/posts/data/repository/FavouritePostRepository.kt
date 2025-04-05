package com.test.posts.data.repository

import com.test.posts.data.source.local.FavouritePostLocalDataSource
import com.test.posts.data.source.local.model.FavouriteLocalPost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritePostRepository @Inject constructor(
    private val favouritePostLocalDataSource: FavouritePostLocalDataSource
) {

    fun getPostsFlow(): Flow<List<FavouriteLocalPost>> {
        return favouritePostLocalDataSource.getPostsFlow()
    }

    suspend fun getPosts(): List<FavouriteLocalPost> {
        return favouritePostLocalDataSource.getPosts()
    }

    suspend fun insert(post: FavouriteLocalPost) {
        favouritePostLocalDataSource.insert(post)
    }

    suspend fun deleteById(id: Int) {
        favouritePostLocalDataSource.deleteById(id)
    }

    suspend fun deleteAll() {
        favouritePostLocalDataSource.deleteAll()
    }
}