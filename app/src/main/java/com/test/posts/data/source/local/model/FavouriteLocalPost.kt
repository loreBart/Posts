package com.test.posts.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.posts.data.model.Post

@Entity(tableName = "posts")
data class FavouriteLocalPost(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "body")
    val body: String
)

fun FavouriteLocalPost.toPost(): Post {
    return Post(id = id, userId = userId, title = title, body = body, isFavourite = true)
}
