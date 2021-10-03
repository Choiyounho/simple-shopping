package com.soten.shop.domain.product

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, Int> {

	override fun findById(id: Int?): Optional<Product>

	fun findAllByOrderByIdDesc(pageable: Pageable): List<Product>

	fun findByCategoryIdAndIdGreaterThanOrderByIdDesc(
		categoryId: Int?, id: Int, pageable: Pageable
	): List<Product>

	fun findByCategoryIdAndIdLessThanOrderByIdDesc(
		categoryId: Int?, id: Int, pageable: Pageable
	): List<Product>

	fun findByIdGreaterThanAndNameLikeOrderByIdDesc(
		id: Int, keyword: String, pageable: Pageable
	): List<Product>

	fun findByIdLessThanAndNameLikeOrderByIdDesc(
		id: Int, keyword: String, pageable: Pageable
	): List<Product>

	fun findAllByCategoryIdOrderByIdDesc(categoryId: Int): List<Product>

}
