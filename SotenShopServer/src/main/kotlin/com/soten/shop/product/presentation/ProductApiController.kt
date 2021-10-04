package com.soten.shop.product.presentation

import com.soten.shop.common.ApiResponse
import com.soten.shop.common.ShopException
import com.soten.shop.product.application.ProductRegistrationService
import com.soten.shop.product.application.ProductService
import com.soten.shop.product.domain.Product
import com.soten.shop.product.dto.ProductRegistrationRequest
import com.soten.shop.product.dto.toProductListItemResponse
import com.soten.shop.product.dto.toProductResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("soten")
class ProductApiController @Autowired constructor(
    private val productRegistration: ProductRegistrationService,
    private val productService: ProductService,
) {

    @GetMapping("/products/recommend")
    fun getAll(@RequestParam(required = false) limit: Int?): ApiResponse {
        return productService.getAllProduct(limit ?: 10)
            .let {
                ApiResponse.ok(it)
            }
    }

    @GetMapping("/product/category/{categoryId}")
    fun getAllCategoryId(@PathVariable categoryId: Int) =
        productService.getAllCategoryId(categoryId).let { ApiResponse.ok(it) }

    @PostMapping("/register/products/")
    fun register(
        @RequestBody request: ProductRegistrationRequest,
    ) = ApiResponse.ok(productRegistration.register(request))

    @GetMapping("/products")
    fun search(@RequestParam productId: Long,
               @RequestParam(required = false) categoryId: Int?,
               @RequestParam direction: String,
               @RequestParam(required = false) keyword: String?,
               @RequestParam(required = false) limit: Int?,
    ): ApiResponse {
        return productService
            .search(categoryId, productId, direction, keyword, limit ?: 10)
            .mapNotNull(Product::toProductListItemResponse)
            .let { ApiResponse.ok(it) }
    }

    @GetMapping("/products/{id}")
    fun get(@PathVariable id: Long): ApiResponse {
        return productService.get(id)?.let {
            ApiResponse.ok(it.toProductResponse())
        } ?: throw ShopException("상품 정보를 찾을 수 없습니다.")
    }

    @PatchMapping("/products/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody product: Product): ApiResponse {
        return productService.updateProduct(id, product.name, product.description, product.price, product.status)
            .let {
                ApiResponse.ok(it.toProductResponse())
            }
    }
}
