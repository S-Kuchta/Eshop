package sk.kuchta.eshop.controller.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kuchta.eshop.api.dto.specification.request.ProductSpecificationRequest;
import sk.kuchta.eshop.api.dto.specification.response.ProductSpecificationResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.api.service.product.ProductService;
import sk.kuchta.eshop.api.service.specification.ProductSpecificationService;
import sk.kuchta.eshop.implementation.entity.product.Product;

@RestController
@RequestMapping("specification")
public class ProductSpecificationController {

    private final ProductService productService;
    private final ProductSpecificationService productSpecificationService;


    public ProductSpecificationController(ProductService productService, ProductSpecificationService productSpecificationService) {
        this.productService = productService;
        this.productSpecificationService = productSpecificationService;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserAccountSaveResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/create/{productId}")
    public ResponseEntity<ProductSpecificationResponse> save(@RequestBody ProductSpecificationRequest request, @PathVariable long productId) {
        Product product = productService.findByIdInternal(productId);
        ProductSpecificationResponse response = productSpecificationService.save(request, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
