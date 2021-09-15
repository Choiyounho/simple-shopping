package com.soten.shop.domain.product

import com.soten.shop.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "product_image")
class ProductImage(
    var path: String,
    var productId: Long? = null
) : BaseEntity()
