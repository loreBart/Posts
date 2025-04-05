package com.test.posts.ui.screen.postdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.posts.data.model.Post
import com.test.posts.data.model.toFavourite
import com.test.posts.data.repository.FavouritePostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val favouritePostRepository: FavouritePostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun toggleIsFavourite(post: Post) {
        viewModelScope.launch {
            val id = post.id
            val favouritesPost = favouritePostRepository.getPosts()
            val isFavourite = favouritesPost.map { it.id }.contains(id)
            if (isFavourite) {
                favouritePostRepository.deleteById(id)
            } else {
                favouritePostRepository.insert(post.toFavourite())
            }
            _uiState.update { it.copy(isPostFavourite = !isFavourite) }
        }
    }

    fun initUiState(post: Post) {
        viewModelScope.launch {
            val id = post.id
            val favouritesPost = favouritePostRepository.getPosts()
            val isFavourite = favouritesPost.map { it.id }.contains(id)
            _uiState.update { it.copy(isPostFavourite = isFavourite) }
        }
    }
}
