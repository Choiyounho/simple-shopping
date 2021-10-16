package com.soten.shop.product.application

import com.soten.shop.common.ShopException
import com.soten.shop.product.domain.Product
import com.soten.shop.product.domain.ProductRepository
import com.soten.shop.product.domain.ProductStatus
import com.soten.shop.product.dto.ProductRegistrationRequest
import com.soten.shop.product.dto.ProductSearchCondition
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ProductService(private val productRepository: ProductRepository) {

    fun get(id: Int): Product {
        return productRepository.findById(id)
    }

    fun searchProducts(keyword: String, limit: Int, pageNumber: Int): Page<Product> {
        val pageable = PageRequest.of(0, limit)
        return productRepository.findByNameContaining(keyword, pageable)
    }

    fun getAllProduct(limit: Int): List<Product> {
        val pageable = PageRequest.of(0, limit)
        return productRepository.findAllByOrderByIdDesc(pageable)
    }

    fun getAllCategoryId(categoryId: Int, limit: Int, pageNumber: Int): Page<Product> {
        val pageable = PageRequest.of(pageNumber, limit)
        return productRepository.findAllByCategoryIdOrderByIdDesc(categoryId, pageable)
    }

    @Transactional
    fun updateProduct(id: Long, name: String, description: String, price: Int, status: ProductStatus): Product {
        val product = productRepository.findById(id).orElseThrow { ShopException("not found product $id") }
        product.updateInformation(name, description, price, status)
        return product
    }

    @Transactional
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
    userId,
    images
)
