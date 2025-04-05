package com.test.posts.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val isFavourite: Boolean
) : Parcelable {
    companion object {
        val EMPTY: Post = Post(0, 0, "","", false)
    }
}
