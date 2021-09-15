package com.soten.shop.domain.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository : JpaRepository<ProductImage, Long> {
    fun findByIdIn(imageIds: List<Long>): MutableList<ProductImage>
}
