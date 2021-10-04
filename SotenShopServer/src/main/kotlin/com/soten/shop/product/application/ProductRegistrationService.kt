package com.soten.shop.product.application

import com.soten.shop.common.ShopException
import com.soten.shop.product.domain.Product
import com.soten.shop.product.domain.ProductRepository
import com.soten.shop.product.domain.ProductStatus
import com.soten.shop.product.dto.ProductRegistrationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductRegistrationService (
	private val productRepository: ProductRepository,
) {

	fun register(request: ProductRegistrationRequest): Product {
		return request.toProduct().run(::save)
			?: throw ShopException(
				"상품 등록에 필요한 사용자 정보가 존재하지 않습니다."
			)
	}

	private fun save(product: Product) = productRepository.save(product)

	fun ProductRegistrationRequest.toProduct() : Product {
		return Product(
			name = name,
			description = description,
			price = price,
			categoryId = categoryId,
			updatedAt = LocalDateTime.now(),
			status = ProductStatus.SELLABLE,
			images = imagePath,
			userId = userId,
		)
	}
}

