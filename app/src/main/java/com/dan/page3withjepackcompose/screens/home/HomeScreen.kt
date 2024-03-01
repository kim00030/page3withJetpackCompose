package com.dan.page3withjepackcompose.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.dan.page3withjepackcompose.navigation.Screen
import com.dan.page3withjepackcompose.screens.common.ListContent

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allImages = homeViewModel.getAllImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(route = Screen.Search.router)
                }
            )
        },
        content = {
            ListContent(items = allImages)
        }
    )
}

@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), homeViewModel = hiltViewModel())
}