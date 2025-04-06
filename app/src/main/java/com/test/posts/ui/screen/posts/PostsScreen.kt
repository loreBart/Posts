package com.test.posts.ui.screen.posts

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.posts.R
import com.test.posts.data.model.Post
import com.test.posts.ui.component.ErrorState
import com.test.posts.ui.component.InfiniteListHandler
import com.test.posts.ui.component.LoadingIndicator
import com.test.posts.ui.theme.AppTheme
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
    fun PostsScreen(
    onNavigateDetails: (Post) -> Unit,
    viewModel: PostsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { PostsTopBar() },
    ) { scaffoldPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(scaffoldPadding)
        ) {
            when (uiState.ui) {
                UiState.None -> {}
                UiState.LoadingFirst -> {
                    LoadingIndicator()
                }
                is UiState.ErrorFirst -> {
                    ErrorState(
                        stringResource(R.string.no_internet_connection)
                    )
                }
                is UiState.Success -> {
                    PostsScreenContent(
                        onNavigateDetails = onNavigateDetails,
                        viewModel = viewModel,
                        uiState = uiState
                    )
                }

            }
        }
    }
}

@Composable
fun PostsScreenContent(
    onNavigateDetails: (Post) -> Unit,
    viewModel: PostsViewModel,
    uiState: PostsUiState
) {
    val page by viewModel.page.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        val lazyListState = rememberLazyStaggeredGridState()
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 12.dp,
            state = lazyListState
        ) {
            items(uiState.posts.size) { index ->
                val post = uiState.posts[index]
                PostItem(
                    post = post,
                    onPostClick = {
                        Timber.tag("###").d("Clicked post $post")
                        onNavigateDetails.invoke(post)
                    },
                    onClickFavouritePost = { p ->
                        viewModel.toggleIsFavourite(p)
                    }
                )
            }
            if (uiState.canLoadMore) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .progressSemantics()
                                .size(32.dp),
                            strokeWidth = 3.dp
                        )
                    }
                }
            }
        }
        InfiniteListHandler(
            lazyListState = lazyListState,
            canLoadMore = uiState.canLoadMore
        ) {
            if (uiState.canLoadMore) {
                val p = page
                val n = p + 1
                viewModel.loadPage(n)
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onPostClick: (Post) -> Unit,
    onClickFavouritePost: (Post) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        onClick = { onPostClick.invoke(post) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row {
                Text(
                    text = post.id.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        onClickFavouritePost(post)
                        Log.d("###", "onClicked <<<<FavouritePost>>>> POST ID: ${post.id}")
                    }
                ) {
                    Icon(
                        imageVector = if (post.isFavourite) {
                            Icons.Rounded.Favorite
                        } else {
                            Icons.Rounded.FavoriteBorder
                        },
                        contentDescription = stringResource(R.string.favourite),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = post.body.split("\n")[0],
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsTopBar() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSearch by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }

    Crossfade(
        modifier = Modifier.animateContentSize(),
        targetState = isSearch,
        label = stringResource(R.string.top_bar_search)
    ) { target ->
        if (!target) {
            TopAppBar(
                title = { Text("Cards") },
                actions = {
                    IconButton(onClick = { isSearch = !isSearch } ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.top_bar_search)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        } else {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        val height = placeable.height * (1 - scrollBehavior.state.collapsedFraction)
                        layout(placeable.width, height.roundToInt()) {
                            placeable.place(0, 0)
                        }
                    },
                value = value,
                placeholder = { Text("Enter card name") },
                onValueChange = { value = it },
                leadingIcon = {
                    IconButton(onClick = { isSearch = !isSearch} ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.top_bar_back)
                        )
                    }
                },
                trailingIcon = if (value.isNotBlank()) {
                    {
                        IconButton(onClick = { value = "" } ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.top_bar_back)
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    }
    /*
    SearchBar(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                onSearch = {
                    onSearch(textFieldState.text.toString())
                    expanded = false
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text("Search") }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    )
    */
    /*
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.posts_screen),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
     */
}

@Preview
@Composable
fun PostsTopBarPreview() {
    AppTheme {
        Surface {
            PostsTopBar()
        }
    }
}
