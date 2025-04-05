package com.test.posts.data.model

import android.os.Parcelable
import com.test.posts.data.source.local.model.FavouriteLocalPost
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isFavourite: Boolean
) : Parcelable {
    companion object {
        val EMPTY: Post = Post(0, 0, "","", false)
    }
}

fun Post.toFavourite(): FavouriteLocalPost {
    return FavouriteLocalPost(id = id, userId = userId, title = title, body = body)
}
