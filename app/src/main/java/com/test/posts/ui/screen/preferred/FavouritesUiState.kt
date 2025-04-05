package com.test.posts.ui.screen.preferred

import com.test.posts.data.model.Post

data class FavouritesUiState(
    val favourites: List<Post> = emptyList()
)