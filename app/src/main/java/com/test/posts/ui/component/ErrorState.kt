package com.test.posts.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.test.posts.R
import com.test.posts.ui.theme.AppTheme

@Composable
fun ErrorState(
    message: String?,
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(R.drawable.error_state),
        title = stringResource(R.string.error_occurred),
        subtitle = {
            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview(heightDp = 480)
private fun ErrorStatePreview() {
    AppTheme {
        ErrorState(
            message = "No internet connection"
        )
    }
}
