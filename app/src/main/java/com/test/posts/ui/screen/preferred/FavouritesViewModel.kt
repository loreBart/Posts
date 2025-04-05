package com.test.posts.ui.screen.preferred

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.test.posts.ui.screen.posts.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()
}
