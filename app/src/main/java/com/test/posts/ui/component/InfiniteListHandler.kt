package com.test.posts.ui.component

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun InfiniteListHandler(
    lazyListState: LazyStaggeredGridState,
    canLoadMore: Boolean,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
            val cond = totalItems > 0 && lastVisibleItemIndex >= (totalItems - 1)
            Pair(cond, totalItems)
        }
    }
    val loadedPages = remember {
        mutableListOf<Int>()
    }
    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect { load ->
                if (load.first) {
                    if (!loadedPages.contains(load.second)) {
                        loadedPages.add(load.second)
                        if (canLoadMore) {
                            onLoadMore()
                        }
                    }
                }
            }
    }
}