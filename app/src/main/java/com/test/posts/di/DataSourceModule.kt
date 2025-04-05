package com.test.posts.di

import com.test.posts.data.source.local.FavouritePostLocalDataSource
import com.test.posts.data.source.local.FavouritePostLocalDataSourceImpl
import com.test.posts.data.source.local.dao.FavouritePostDao
import com.test.posts.data.source.network.PostApi
import com.test.posts.data.source.network.PostNetworkDataSource
import com.test.posts.data.source.network.PostPostNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun providePostNetworkDataSource(api: PostApi): PostNetworkDataSource
        = PostPostNetworkDataSourceImpl(api)

    @Provides
    @Singleton
    fun providePostLocalDataSource(favouritePostDao: FavouritePostDao): FavouritePostLocalDataSource {
        return FavouritePostLocalDataSourceImpl(favouritePostDao = favouritePostDao)
    }

}
