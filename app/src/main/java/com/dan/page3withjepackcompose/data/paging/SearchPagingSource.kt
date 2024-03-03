package com.dan.page3withjepackcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dan.page3withjepackcompose.data.remote.UnsplashApi
import com.dan.page3withjepackcompose.model.UnsplashImage
import com.dan.page3withjepackcompose.util.Constants

class SearchPagingSource (
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashImage>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: 1

        return try {
            val response =
                unsplashApi.searchImages(query = query, perPage = Constants.ITEMS_PER_PAGE)
            val isEndOfPaginationReached = response.images.isEmpty()
            if (response.images.isNotEmpty()) {
                LoadResult.Page(
                    data = response.images,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (isEndOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}