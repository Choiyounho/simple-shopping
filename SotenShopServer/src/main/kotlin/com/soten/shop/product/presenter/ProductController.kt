package com.soten.shop.product.presenter

import com.soten.shop.common.ApiResponse
import com.soten.shop.product.application.ProductService
import com.soten.shop.product.domain.Product
import com.soten.shop.product.dto.ProductRegistrationRequest
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/soten")
class ProductApiController(private val productService: ProductService) {

	@GetMapping("/products/recommend")
	fun getAll(@RequestParam(required = false) limit: Int?): ApiResponse {
		return productService.getAllProduct(limit ?: 10)
			.let {
				ApiResponse.ok(it)
			}
	}

	@GetMapping("/product/category/{categoryId}")
	fun getAllCategoryId(
		@PathVariable categoryId: Int,
		@RequestParam pageNumber: Int,
		@RequestParam(required = false) limit: Int?
	): Page<Product> {
		return productService.getAllCategoryId(categoryId, limit ?: 5, pageNumber)
	}

	@PostMapping("/register/products/")
	fun register(
		@RequestBody request: ProductRegistrationRequest
	) = ApiResponse.ok(productService.register(request))


	@GetMapping("/products/{id}")
	fun get(@PathVariable id: Int): ApiResponse {
		val product = productService.get(id)
		return ApiResponse.ok(product)
	}

	@GetMapping("/products/search")
	fun searchKeyword(
		@RequestParam(value = "keyword") keyword: String,
		@RequestParam pageNumber: Int,
		@RequestParam(required = false) limit: Int?
	): Page<Product> {
		return productService.searchProducts(keyword, limit ?: 5, pageNumber)
	}

}
