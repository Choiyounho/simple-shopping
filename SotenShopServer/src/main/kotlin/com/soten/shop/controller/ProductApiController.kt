package com.soten.shop.controller

import com.soten.shop.common.ApiResponse
import com.soten.shop.common.ShopException
import com.soten.shop.domain.product.Product
import com.soten.shop.domain.product.ProductService
import com.soten.shop.domain.product.registration.ProductImageService
import com.soten.shop.domain.product.registration.ProductRegistrationRequest
import com.soten.shop.domain.product.registration.ProductRegistrationService
import com.soten.shop.domain.product.toProductListItemResponse
import com.soten.shop.domain.product.toProductResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("soten")
class ProductApiController @Autowired constructor(
	private val productImageService: ProductImageService,
	private val productRegistration: ProductRegistrationService,
	private val productService: ProductService
) {

	@GetMapping("/productss")
	fun getAll() = productService.getAllProduct().let { ApiResponse.ok(it) }

	@GetMapping("/product/category/{categoryId}")
	fun getAllCategoryId(@PathVariable categoryId: Int) =
		productService.getAllCategoryId(categoryId).let { ApiResponse.ok(it) }

	@PostMapping("/product_images")
	fun uploadImage(image: MultipartFile) =
		ApiResponse.ok(productImageService.uploadImage(image))

	@PostMapping("/products")
	fun register(
		@RequestBody request: ProductRegistrationRequest
	) = ApiResponse.ok(productRegistration.register(request))

	@GetMapping("/products")
	fun search(
		@RequestParam productId: Long,
		@RequestParam(required = false) categoryId: Int?,
		@RequestParam direction: String,
		@RequestParam(required = false) keyword: String?,
		@RequestParam(required = false) limit: Int?
	) = productService
		.search(categoryId, productId, direction, keyword, limit ?: 10)
		.mapNotNull(Product::toProductListItemResponse)
		.let { ApiResponse.ok(it) }

	@GetMapping("/products/{id}")
	fun get(@PathVariable id: Long) = productService.get(id)?.let {
		ApiResponse.ok(it.toProductResponse())
	} ?: throw ShopException("상품 정보를 찾을 수 없습니다.")

	@PatchMapping("/products/{id}")
	fun update(
		@PathVariable id: Long,
		@Valid @RequestBody product: Product
	) = productService.updateProduct(id, product.name, product.description, product.price, product.status).let {
		ApiResponse.ok(it.toProductResponse())
	}

}
