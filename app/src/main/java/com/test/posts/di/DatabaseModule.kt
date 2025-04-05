package com.test.posts.di

import android.content.Context
import androidx.room.Room
import com.test.posts.data.source.local.PostDatabase
import com.test.posts.data.source.local.dao.FavouritePostDao
import com.test.posts.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase): FavouritePostDao {
        return database.postDao()
    }

    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): PostDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PostDatabase::class.java,
            Constants.POST_DATABASE_NAME
        ).build()
    }

}