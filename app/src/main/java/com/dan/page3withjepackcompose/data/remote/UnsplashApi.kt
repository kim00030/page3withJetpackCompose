package com.dan.page3withjepackcompose.data.remote

import com.dan.page3withjepackcompose.BuildConfig
import com.dan.page3withjepackcompose.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    /*
    According to unsplash document: https://unsplash.com/documentation
    section of List photos API
     */
    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): List<UnsplashImage>


    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): List<UnsplashImage>
}