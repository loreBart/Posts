package com.test.posts.ui.screen.posts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.posts.data.Async
import com.test.posts.data.model.Post
import com.test.posts.data.model.toFavourite
import com.test.posts.data.repository.FavouritePostRepository
import com.test.posts.data.repository.PostRepository
import com.test.posts.data.source.local.model.FavouriteLocalPost
import com.test.posts.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val favouritePostRepository: FavouritePostRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    private val _page = MutableStateFlow(1)
    private val _query = MutableStateFlow("")
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(postRepository.loadPosts(), favouritePostRepository.getPostsFlow(), _page, _query) { posts, favourites, page, query ->
                Log.d("###", "ON COMBINE -> posts: ${posts.javaClass.simpleName}, favourites: ${favourites.size}, page: $page, query: $query")

                when (posts) {
                    is Async.Loading -> {
                        _uiState.update {
                            it.copy(ui = UiState.LoadingFirst)
                        }
                    }
                    is Async.Error -> {
                        _uiState.update {
                            it.copy(ui = UiState.ErrorFirst(posts.e))
                        }
                    }
                    is Async.Success -> {
                        val postList = posts.data
                        val canLoadMore = page*Constants.PAGE_SIZE < postList.size
                        val size = min(page*Constants.PAGE_SIZE, postList.size)
                        _uiState.update {
                            it.copy(
                                ui = UiState.Success(page),
                                posts = postList.subList(0, size).map {
                                    p -> Post(
                                            userId = p.userId,
                                            id = p.id,
                                            title = p.title,
                                            body = p.body,
                                            isFavourite = isFavouritePost(p.id, favourites))
                                },
                                canLoadMore = canLoadMore,
                                page = page
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    fun loadPage(nextPage: Int) {
        Log.d("###", "loadPage NEXT PAGE -> $nextPage")
        viewModelScope.launch {
            // Simulate network request
            delay(1000)
            _page.update { nextPage }
        }
    }
    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }
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
        }
    }

    private fun isFavouritePost(id: Int, favourites: List<FavouriteLocalPost>): Boolean {
        return favourites.map { it.id }.contains(id)
    }

}
