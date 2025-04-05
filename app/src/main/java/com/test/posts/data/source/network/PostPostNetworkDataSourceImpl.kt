package com.test.posts.data.source.network

import com.test.posts.data.Async
import com.test.posts.data.source.network.model.NetworkPost
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostPostNetworkDataSourceImpl @Inject constructor(private val api: PostApi) : PostNetworkDataSource {

    override suspend fun loadPosts(): Flow<Async<List<NetworkPost>>> = flow {
        emit(Async.Loading)
        try {
            emit(Async.Success(api.loadPosts()))
        } catch (e: Exception) {
            emit(Async.Error(e))
        }
    }

}
