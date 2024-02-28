package com.dan.page3withjepackcompose.di

import android.content.Context
import androidx.room.Room
import com.dan.page3withjepackcompose.data.local.UnsplashDatabase
import com.dan.page3withjepackcompose.data.local.dao.UnsplashImageDao
import com.dan.page3withjepackcompose.data.local.dao.UnsplashRemoteKeysDao
import com.dan.page3withjepackcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesUnsplashDatabase(@ApplicationContext context: Context): UnsplashDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = UnsplashDatabase::class.java,
            name = Constants.UNSPLASH_DATA_BASE
        ).build()
    }

    @Provides
    @Singleton
    fun providesUnsplashImageDao(database: UnsplashDatabase): UnsplashImageDao {
        return database.unsplashImageDao()
    }

    @Provides
    @Singleton

    fun providesUnsplashRemoteKeys(database: UnsplashDatabase): UnsplashRemoteKeysDao {
        return database.unsplashRemoteKeysDao()
    }
}