package com.soten.shop.product.dto

import com.soten.shop.common.ShopException
import com.soten.shop.product.domain.Product

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val status: String,
    val sellerId: Int,
    val images: List<String>
)

fun Product.toProductResponse() = id?.let { it ->
    ProductResponse(
        it,
        name,
        description,
        price,
        status.name,
        userId,
        images
    )
} ?: throw ShopException("상품 정보를 찾을 수 없습니다.")
