package com.soten.shop.product.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {


	fun findById(id: Int): Product
	fun findAllByOrderByIdDesc(pageable: Pageable): List<Product>
	fun findByNameContaining(keyword: String, pageable: Pageable): Page<Product>
	fun findAllByCategoryIdOrderByIdDesc(categoryId: Int, pageable: Pageable): Page<Product>
}
