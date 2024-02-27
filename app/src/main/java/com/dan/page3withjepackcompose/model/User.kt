package com.dan.page3withjepackcompose.model

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("links")
    @Embedded
    val userLinks: UserLinks,
    val userName: String
)
/*
why adding @Embedded on userLinks?
Because this userLinks is customer class created. Room Db does not know
how to store this data in the  DB
 */