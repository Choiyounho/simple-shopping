package com.soten.shop.domain.product.registration

import com.soten.shop.common.ShopException
import com.soten.shop.domain.product.Product
import com.soten.shop.domain.product.ProductRepository
import com.soten.shop.domain.product.ProductStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductRegistrationService @Autowired constructor(
	private val productRepository: ProductRepository,
) {

	fun register(request: ProductRegistrationRequest): Product {
		return request.toProduct().run(::save)
			?: throw ShopException(
				"상품 등록에 필요한 사용자 정보가 존재하지 않습니다."
			)
	}

	private fun save(product: Product) = productRepository.save(product)

}

private fun ProductRegistrationRequest.toProduct() = Product(
	name,
	description,
	price,
	categoryId,
	LocalDateTime.now(),
	ProductStatus.SELLABLE,
	imagePath,
	userId
)
