package com.test.posts.data.repository

import com.test.posts.data.Async
import com.test.posts.data.source.network.PostNetworkDataSource
import com.test.posts.data.source.network.model.NetworkPost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postNetworkDataSource: PostNetworkDataSource
) {

    suspend fun loadPosts(): Flow<Async<List<NetworkPost>>> {
        return postNetworkDataSource.loadPosts()
    }
}
