package com.dan.page3withjepackcompose.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dan.page3withjepackcompose.util.Constants

/*
This model class will also represent a table in Room DB
 */
@Entity(tableName = Constants.UNSPLASH_IMAGE_TABLE)
data class UnsplashImage(
    //RoomDB needs primary key, so I use id from server as Primary Key
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: Urls,
    val likes: Int,
    @Embedded
    val user: User
)
