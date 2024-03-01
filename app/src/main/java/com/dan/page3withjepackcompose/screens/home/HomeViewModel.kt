package com.dan.page3withjepackcompose.screens.home

import androidx.lifecycle.ViewModel
import com.dan.page3withjepackcompose.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val getAllImages = repository.getAllImages()
}