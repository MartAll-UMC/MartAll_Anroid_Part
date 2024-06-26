package com.org.martall.models

import com.google.gson.annotations.SerializedName

data class MartDataDTO(
    @SerializedName("martId") val martId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("followersCount") val followersCount: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("bookmark") val bookmarkYn: Boolean,
    @SerializedName("items") val items: List<MartItemDTO>
)

class MartItemDTO(
    @SerializedName("itemId") val itemId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("price") val price: Int,
    @SerializedName("isLike") var likeYn: Boolean
)
