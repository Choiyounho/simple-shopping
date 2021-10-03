package com.soten.shop.domain.product.service

import com.soten.shop.common.ShopException
import com.soten.shop.domain.product.Product
import com.soten.shop.domain.product.ProductRegistrationRequest
import com.soten.shop.domain.product.ProductRepository
import com.soten.shop.domain.product.ProductStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class ProductService @Autowired constructor(
	private val productRepository: ProductRepository
) {

	fun get(id: Int) = productRepository.findByIdOrNull(id)

	fun search(
		categoryId: Int?,
		productId: Int,
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
	fun register(request: ProductRegistrationRequest): Product {
		return request.toProduct().run(::save)
			?: throw ShopException(
				"상품 등록에 필요한 사용자 정보가 존재하지 않습니다."
			)
	}

	private fun save(product: Product) = productRepository.save(product)

	@Transactional
	fun updateProduct(id: Int, name: String, description: String, price: Int, status: ProductStatus): Product {
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

private fun ProductRegistrationRequest.toProduct() = Product(
	name,
	description,
	price,
	categoryId,
	LocalDateTime.now(),
	ProductStatus.SELLABLE,
	userId,
	images
)
