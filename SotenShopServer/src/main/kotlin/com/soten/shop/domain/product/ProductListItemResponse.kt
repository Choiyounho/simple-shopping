package com.soten.shop.domain.product

data class ProductListItemResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val status: String,
    val sellerId: Int,
    val imagePaths: String
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

