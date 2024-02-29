package com.dan.page3withjepackcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dan.page3withjepackcompose.data.local.dao.UnsplashImageDao
import com.dan.page3withjepackcompose.data.local.dao.UnsplashRemoteKeysDao
import com.dan.page3withjepackcompose.model.UnsplashImage
import com.dan.page3withjepackcompose.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1, exportSchema = false)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}