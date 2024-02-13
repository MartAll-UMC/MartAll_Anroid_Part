package com.org.martall.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Response(
    val timeStamp: String,
    val status: Int,
    val message: String,
    val data: List<Item>,
    val success: Boolean
)

data class Item(
    val itemId: Int,
    val categoryId: Int,
    val itemName: String,
    val price: Int,
    val content: String,
    var isLiked: Boolean = false
)

