package com.soten.shop.product.domain

import com.soten.shop.product.domain.Product
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, Long> {
	override fun findById(id: Long?): Optional<Product>
	fun findAllByOrderByIdDesc(pageable: Pageable): List<Product>
	fun findByCategoryIdAndIdGreaterThanOrderByIdDesc(categoryId: Int?, id: Long, pageable: Pageable): List<Product>
	fun findByCategoryIdAndIdLessThanOrderByIdDesc(categoryId: Int?, id: Long, pageable: Pageable): List<Product>
	fun findByIdGreaterThanAndNameLikeOrderByIdDesc(id: Long, keyword: String, pageable: Pageable): List<Product>
	fun findByIdLessThanAndNameLikeOrderByIdDesc(id: Long, keyword: String, pageable: Pageable): List<Product>
	fun findAllByCategoryIdOrderByIdDesc(categoryId: Int): List<Product>
}
