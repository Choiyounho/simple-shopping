package com.soten.shop.product.domain

import com.soten.shop.common.BaseEntity
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "product")
class Product(

    @Column(length = 40)
    var name: String,

    @Column(length = 500)
    var description: String,

    var price: Int,

    var categoryId: Int,

    override var updatedAt: LocalDateTime?,

    @Enumerated(EnumType.STRING)
    var status: ProductStatus,

    @Length(max = 15500)
    var images: String,

    val userId: Int

) : BaseEntity() {

    fun updateInformation(name: String, description: String, price: Int, status: ProductStatus) {
        this.name = name
        this.description = description
        this.price = price
        this.status = status
        this.updatedAt = LocalDateTime.now()
    }
}
