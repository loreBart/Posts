package com.test.posts.ui.screen.preferred

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.posts.data.model.Post
import com.test.posts.data.repository.FavouritePostRepository
import com.test.posts.data.source.local.model.toPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritePostRepository: FavouritePostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState = favouritePostRepository.getPostsFlow().map { favourites ->
        favourites.map { p ->
            p.toPost()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun removeIsFavourite(post: Post) {
        viewModelScope.launch {
            val id = post.id
            favouritePostRepository.deleteById(id)
        }
    }
}
