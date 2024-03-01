package com.dan.page3withjepackcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dan.page3withjepackcompose.data.local.UnsplashDatabase
import com.dan.page3withjepackcompose.data.remote.UnsplashApi
import com.dan.page3withjepackcompose.model.UnsplashImage
import com.dan.page3withjepackcompose.model.UnsplashRemoteKeys
import com.dan.page3withjepackcompose.util.Constants.ITEMS_PER_PAGE

/*
RemoteMediator is to automatically fetch data from API and store the data into Room DB
class UnsplashRemoteMediator() is instantiated by Repository that passes unsplashApi and unsplashDatabase

 */
@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) : RemoteMediator<Int, UnsplashImage>() {

    private val unsplashImageDao = unsplashDatabase.unsplashImageDao()
    private val unsplashRemoteKeysDao = unsplashDatabase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }

            //Make request to get all photos from server
            val response = unsplashApi.getAllPhotos(page = currentPage, perPage = ITEMS_PER_PAGE)
            val isEndOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (isEndOfPaginationReached) null else currentPage + 1

            unsplashDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteAllImages()
                    unsplashRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                unsplashRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                unsplashImageDao.addImages(images = response)
            }

            MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                unsplashRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { unsplashImage ->
            unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImage>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { unsplashImage ->
            unsplashRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
        }
    }
}