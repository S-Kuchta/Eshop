package sk.kuchta.eshop.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kuchta.eshop.api.dto.product.request.ProductSaveRequest;
import sk.kuchta.eshop.api.dto.product.response.ProductSaveResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.api.service.category.CategoryService;
import sk.kuchta.eshop.api.service.product.ProductService;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductSaveResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/{categoryId}/create")
    public ResponseEntity<ProductSaveResponse> save(@PathVariable long categoryId, @RequestBody ProductSaveRequest request) {
        final Category category = categoryService.findByIdInternal(categoryId);
        final ProductSaveResponse response = productService.save(request, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
