package com.test.posts.data.source.local

import com.test.posts.data.source.local.dao.FavouritePostDao
import com.test.posts.data.source.local.model.FavouriteLocalPost
import kotlinx.coroutines.flow.Flow

class FavouritePostLocalDataSourceImpl(private val favouritePostDao: FavouritePostDao) : FavouritePostLocalDataSource {

    override fun getPosts(): Flow<List<FavouriteLocalPost>> {
        return favouritePostDao.getPosts()
    }

    override suspend fun insert(post: FavouriteLocalPost) {
        favouritePostDao.insert(post)
    }

    override suspend fun deleteById(id: Long) {
        favouritePostDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        favouritePostDao.deleteAll()
    }
}