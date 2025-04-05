package com.test.posts.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.posts.data.source.local.dao.FavouritePostDao
import com.test.posts.data.source.local.model.FavouriteLocalPost

@Database(
    version = 1,
    entities = [FavouriteLocalPost::class],
)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): FavouritePostDao
}
