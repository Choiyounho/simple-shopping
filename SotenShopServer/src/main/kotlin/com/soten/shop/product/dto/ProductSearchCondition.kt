package com.soten.shop.product.dto

class ProductSearchCondition(
    val categoryIdIsNotNull: Boolean,
    val direction: String,
    val hasKeyword: Boolean = false
)