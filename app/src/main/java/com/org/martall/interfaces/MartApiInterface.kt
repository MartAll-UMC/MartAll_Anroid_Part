package com.org.martall.interfaces

import com.org.martall.models.FollowResponseDTO
import com.org.martall.models.MartDetailResponseDTO
import com.org.martall.models.MartListResponseDTO
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MartApiInterface {
    @GET("/mart/shops/all")
    fun getAllShops(): Call<MartListResponseDTO>

    @GET("/mart/shops/{shopid}/detail")
    fun getMartDetail(
        @Path("shopid") shopId: Int)
    : Call<MartDetailResponseDTO>

    @POST("/mart/shops/{shopid}/follow")
    fun followMart(
        @Path("shopid") shopId: Int)
            : Call<FollowResponseDTO>

    @DELETE("/mart/shops/{shopid}/unfollow")
    fun unfollowMart(
        @Path("shopid") shopId: Int)
            : Call<FollowResponseDTO>
}