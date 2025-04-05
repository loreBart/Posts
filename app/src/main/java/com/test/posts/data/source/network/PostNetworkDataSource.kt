package com.test.posts.data.source.network

import com.test.posts.data.Async
import com.test.posts.data.source.network.model.NetworkPost
import kotlinx.coroutines.flow.Flow

interface PostNetworkDataSource {

    suspend fun loadPosts(): Flow<Async<List<NetworkPost>>>

}
