package com.test.posts.ui.screen.preferred

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.posts.R
import com.test.posts.data.model.Post
import timber.log.Timber

@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel(),
    onNavigateDetails: (Post) -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { FavouritesTopBar() },
    ) { scaffoldPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(scaffoldPadding)
        ) {
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
                    items(uiState.size) { index ->
                        val post = uiState[index]
                        FavouritePostItem(
                            post = post,
                            onPostClick = {
                                Timber.tag("###").d("Clicked post $post")
                                onNavigateDetails.invoke(post)
                            },
                            onClickFavouritePost = { p ->
                                viewModel.removeIsFavourite(p)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavouritePostItem(
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
                Spacer(Modifier.weight(1f))

                IconButton(
                    onClick = {
                        onClickFavouritePost(post)
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
                text = post.body,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.favourites_screen),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )
}
