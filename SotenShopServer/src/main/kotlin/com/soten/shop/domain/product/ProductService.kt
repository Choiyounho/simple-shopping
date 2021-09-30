package com.soten.shop.domain.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProductService @Autowired constructor(
	private val productRepository: ProductRepository
) {

	fun get(id: Long) = productRepository.findByIdOrNull(id)

	fun search(
		categoryId: Int?,
		productId: Long,
		direction: String,
		keyword: String?,
		limit: Int
	): List<Product> {
		val pageable = PageRequest.of(0, limit)
		val condition = ProductSearchCondition(
			categoryId != null,
			direction,
			keyword != null
		)

		return when (condition) {
			NEXT_IN_SEARCH -> productRepository
				.findByIdLessThanAndNameLikeOrderByIdDesc(
					productId, "%$keyword%", pageable
				)
			PREV_IN_SEARCH -> productRepository
				.findByIdGreaterThanAndNameLikeOrderByIdDesc(
					productId, "%$keyword%", pageable
				)
			NEXT_IN_CATEGORY -> productRepository
				.findByCategoryIdAndIdLessThanOrderByIdDesc(
					categoryId, productId, pageable
				)
			PREV_IN_CATEGORY -> productRepository
				.findByCategoryIdAndIdGreaterThanOrderByIdDesc(
					categoryId, productId, pageable
				)
			else -> throw IllegalArgumentException("상품 검색 조건 오류")
		}
	}

	@Transactional
	fun updateProduct(id: Long, name: String, description: String, price: Int, status: ProductStatus): Product {
		val product = productRepository.findById(id)
		product.get().updateInformation(name, description, price, status)
		return product.get()
	}

	fun getAllProduct(
		limit: Int
	): List<Product> {
		val pageable = PageRequest.of(0, limit)
		return productRepository.findAllByOrderByIdDesc(pageable)
	}

	fun getAllCategoryId(categoryId: Int): List<Product> {
		return productRepository.findAllByCategoryIdOrderByIdDesc(categoryId)
	}

	data class ProductSearchCondition(
		val categoryIdIsNotNull: Boolean,
		val direction: String,
		val hasKeyword: Boolean = false
	)

	companion object {
		val NEXT_IN_SEARCH = ProductSearchCondition(false, "next", true)
		val PREV_IN_SEARCH = ProductSearchCondition(false, "prev", true)
		val NEXT_IN_CATEGORY = ProductSearchCondition(true, "next")
		val PREV_IN_CATEGORY = ProductSearchCondition(true, "prev")
	}

}
