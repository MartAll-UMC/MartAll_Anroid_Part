package com.org.martall.Model

data class DibsProductResponseDTO(
    val message: String,
    val status: Int,
    val success: Boolean,
    val timeStamp: String,
    val result: Result,
) {

    data class Result(
        val item: List<DibsProducts> // item로 수정
    )

    data class DibsProducts(
        val martShopId: Long,
        val picName: String, // 이미지 경로는 문자열로 설정
        val martName: String,
        val itemId: Int,
        val itemName: String,
        val price: Int,
        var like: Boolean = false // var로 선언할 필요가 없으므로 val로 변경
    )
}
