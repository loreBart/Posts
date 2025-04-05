package com.test.posts.ui.screen.postdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.test.posts.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val post = savedStateHandle.get<Post>("post")
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("###", "ON INIT POST --------> $post")
    }
    fun toggleIsPostFavourite() {
        //if (isFavourite) {

        //}
    }

}
