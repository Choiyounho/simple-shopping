package com.soten.shop.product.dto

import com.soten.shop.product.domain.Product

data class ProductListItemResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val status: String,
    val sellerId: Int,
    val images: List<String>
)

fun Product.toProductListItemResponse() = id?.let {
    ProductListItemResponse(
        it,
        name,
        description,
        price,
        status.name,
        userId,
        images
    )
}

