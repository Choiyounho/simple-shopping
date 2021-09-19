package com.soten.shop.domain.product

import com.soten.shop.domain.jpa.BaseEntity
import javax.persistence.*

@Entity(name = "product")
class Product(

    @Column(length = 40)
    var name: String,

    @Column(length = 500)
    var description: String,

    var price: Int,

    var categoryId: Int,

    @Enumerated(EnumType.STRING)
    var status: ProductStatus,

    @OneToMany
    @JoinColumn(name = "productId")
    var images: MutableList<ProductImage>,

    val userId: Long

) : BaseEntity() {

    fun updateInformation(name: String, description: String, price: Int, status: ProductStatus) {
        this.name = name
        this.description = description
        this.price = price
        this.status = status
    }

}
