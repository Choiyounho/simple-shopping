package com.soten.shop.product.application

import com.soten.shop.common.ShopException
import com.soten.shop.product.domain.Product
import com.soten.shop.product.domain.ProductRepository
import com.soten.shop.product.domain.ProductStatus
import com.soten.shop.product.dto.ProductSearchCondition
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductService(
    private val productRepository: ProductRepository
) {

    fun get(id: Long): Product? {
        return productRepository.findByIdOrNull(id)
    }

    @Transactional
    fun search(categoryId: Int?, productId: Long, direction: String, keyword: String?, limit: Int): List<Product> {
        val pageable = PageRequest.of(0, limit)
        val isCategoryId = categoryId != null
        val isKeyword = keyword != null
        val condition = ProductSearchCondition(isCategoryId, direction, isKeyword)

        return when (condition) {
            NEXT_IN_SEARCH -> productRepository.findByIdLessThanAndNameLikeOrderByIdDesc(
                productId, "%$keyword%", pageable
            )
            PREV_IN_SEARCH -> productRepository.findByIdGreaterThanAndNameLikeOrderByIdDesc(
                productId, "%$keyword%", pageable
            )
            NEXT_IN_CATEGORY -> productRepository.findByCategoryIdAndIdLessThanOrderByIdDesc(
                categoryId, productId, pageable
            )
            PREV_IN_CATEGORY -> productRepository.findByCategoryIdAndIdGreaterThanOrderByIdDesc(
                categoryId, productId, pageable
            )
            else -> throw IllegalArgumentException("상품 검색 조건 오류")
        }
    }

    @Transactional
    fun updateProduct(id: Long, name: String, description: String, price: Int, status: ProductStatus): Product {
        val product = productRepository.findById(id).orElseThrow { ShopException("not found product $id") }
        product.updateInformation(name, description, price, status)
        return product
    }

    fun getAllProduct(limit: Int): List<Product> {
        val pageable = PageRequest.of(0, limit)
        return productRepository.findAllByOrderByIdDesc(pageable)
    }

    fun getAllCategoryId(categoryId: Int): List<Product> {
        return productRepository.findAllByCategoryIdOrderByIdDesc(categoryId)
    }

    companion object {
        val NEXT_IN_SEARCH = ProductSearchCondition(false, "next", true)
        val PREV_IN_SEARCH = ProductSearchCondition(false, "prev", true)
        val NEXT_IN_CATEGORY = ProductSearchCondition(true, "next")
        val PREV_IN_CATEGORY = ProductSearchCondition(true, "prev")
    }
}
