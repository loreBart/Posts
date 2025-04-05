package com.test.posts.data.source.network

import com.test.posts.data.source.network.model.NetworkPost
import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    suspend fun loadPosts(): List<NetworkPost>

}
