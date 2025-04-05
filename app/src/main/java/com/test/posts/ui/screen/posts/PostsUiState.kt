package com.test.posts.ui.screen.posts

import com.test.posts.data.model.Post

sealed interface UiState {
    data object None : UiState
    data object LoadingFirst : UiState
    data class ErrorFirst(val e: Throwable? = null) : UiState
    data class Success(val page: Int) : UiState
}

data class PostsUiState(
    val ui: UiState = UiState.None,
    val query: String = "",
    val posts: List<Post> = emptyList(),
    val page: Int = 1,
    val canLoadMore: Boolean = true
)
