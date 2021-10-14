import com.soten.shop.common.ApiResponse
import com.soten.shop.product.application.ProductService
import com.soten.shop.product.domain.Product
import com.soten.shop.product.dto.ProductRegistrationRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("soten")
class ProductApiController @Autowired constructor(
	private val productService: ProductService
) {

	@GetMapping("/products/recommend")
	fun getAll(
		@RequestParam(required = false) limit: Int?,
	): List<Product> {
		return productService.getAllProduct(limit ?: 10)
	}

	@GetMapping("/product/category/{categoryId}")
	fun getAllCategoryId(@PathVariable categoryId: Int) =
		productService.getAllCategoryId(categoryId).let { ApiResponse.ok(it) }

	@PostMapping("/register/products/")
	fun register(
		@RequestBody request: ProductRegistrationRequest
	) = ApiResponse.ok(productService.register(request))


	@GetMapping("/products/{id}")
	fun get(@PathVariable id: Int): ApiResponse {
		return productService.get(id).let { ApiResponse.ok(it) }
	}

}
