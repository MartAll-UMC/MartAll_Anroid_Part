package com.org.martall.interfaces

import com.org.martall.model.MartDetailResponseDTO
import com.org.martall.model.MartLikedResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemApiService {
    @POST("/item-like/{itemId}")
    fun likedItem(
        @Path("itemId") itemId: Int)
    : Call<MartLikedResponseDTO>

    @DELETE("/item-like/{itemId}")
    fun unLikedItem(
        @Path("itemId") itemId: Int)
    : Call<MartLikedResponseDTO>
}