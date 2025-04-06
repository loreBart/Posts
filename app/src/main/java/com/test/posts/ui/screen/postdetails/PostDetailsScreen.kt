package com.test.posts.ui.screen.postdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.posts.R
import com.test.posts.data.model.Post
import com.test.posts.ui.theme.AppTheme

@Composable
fun PostDetailsScreen(
    post: Post,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.initUiState(post)
    }

    PostDetailsScreen(
        post = post,
        viewModel = viewModel,
        onNavigateUp = onNavigateUp,
        onClickFavouritePost = { viewModel.toggleIsFavourite(post) }
    )
}

@Composable
fun PostDetailsScreen(
    post: Post,
    viewModel: PostDetailsViewModel,
    onNavigateUp: () -> Unit,
    onClickFavouritePost: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            PostDetailsTopBar(
                isPostFavourite = uiState.isPostFavourite,
                onNavigateUp = onNavigateUp,
                onClickFavouritePost = onClickFavouritePost
            )
        }
    ) { scaffoldPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize(),
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsTopBar(
    isPostFavourite: Boolean,
    onNavigateUp: () -> Unit,
    onClickFavouritePost: ()->Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.top_bar_back)
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.details_screen),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            IconButton(onClick = onClickFavouritePost) {
                Icon(
                    imageVector = if (isPostFavourite) {
                        Icons.Rounded.Favorite
                    } else {
                        Icons.Rounded.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.favourite),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    )
}

@Preview
@Composable
fun PostDetailsTopBarPreview() {
    AppTheme {
        Surface {
            PostDetailsTopBar(
                false,
                {}
            ) { }
        }
    }
}
