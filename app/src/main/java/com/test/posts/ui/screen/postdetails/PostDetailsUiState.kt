package com.test.posts.ui.screen.postdetails

import com.test.posts.data.model.Post

data class PostDetailsUiState(
    val isLoading: Boolean=true,
    val post: Post = Post.EMPTY,
    val isPostFavourite: Boolean = false
)
