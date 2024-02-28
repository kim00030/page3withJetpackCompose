package com.dan.page3withjepackcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dan.page3withjepackcompose.util.Constants

@Entity(tableName = Constants.UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
