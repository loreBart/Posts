package com.test.posts.ui.screen.posts

import android.os.Parcelable
import com.test.posts.data.model.Post
import kotlinx.parcelize.Parcelize


sealed interface UiState : Parcelable {
    @Parcelize
    data object None : UiState
    @Parcelize
    data object LoadingFirst : UiState
    @Parcelize
    data class ErrorFirst(val e: Throwable? = null) : UiState
    @Parcelize
    data class LoadingPage(val page: Int) : UiState
    @Parcelize
    data class Success(val page: Int) : UiState
}

data class PostsUiState(
    val ui: UiState = UiState.None,
    val query: String = "",
    val posts: List<Post> = emptyList(),
    val page: Int = 1,
    val canLoadMore: Boolean = false
)
