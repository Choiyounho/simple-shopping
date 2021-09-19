package com.soten.shop.domain.product.registration

import com.soten.shop.common.ShopException
import com.soten.shop.domain.auth.UserContextHolder
import com.soten.shop.domain.product.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductRegistrationService @Autowired constructor(
	private val productRepository: ProductRepository,
	private val productImageRepository: ProductImageRepository,
	private val userContextHolder: UserContextHolder
) {

	fun register(request: ProductRegistrationRequest) =
		userContextHolder.id?.let { userId ->
			val images by lazy { findAndValidateImages(request.imageIds) }
			request.validateRequest()
			request.toProduct(images, userId)
				.run(::save)
		} ?: throw ShopException(
			"상품 등록에 필요한 사용자 정보가 존재하지 않습니다."
		)

	private fun findAndValidateImages(imageIds: List<Long?>) =
		productImageRepository.findByIdIn(imageIds.filterNotNull())
			.onEach { image ->
				if (image.productId != null)
					throw ShopException("이미 등록된 상품입니다.")
			}

	private fun save(product: Product) = productRepository.save(product)

}

private fun ProductRegistrationRequest.validateRequest() = when {
	name.length !in 1..40 ||
			imageIds.size !in 1..4 ||
			imageIds.filterNotNull().isEmpty() ||
			description.length !in 1..500 ||
			price <= 0 ->
		throw ShopException("올바르지 않은 상품 정보입니다")
	else -> {
	}
}

private fun ProductRegistrationRequest.toProduct(
	images: MutableList<ProductImage>,
	userId: Long
) = Product(
	name,
	description,
	price,
	categoryId,
	ProductStatus.SELLABLE,
	images,
	userId
)
