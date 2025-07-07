package sk.kuchta.eshop.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressEditRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.api.service.user.UserAccountService;
import sk.kuchta.eshop.api.service.user.UserAddressService;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;

import java.util.List;

@RestController
@RequestMapping("user-address")
public class UserAddressController {

    private final UserAddressService userAddressService;
    private final UserAccountService userAccountService;

    public UserAddressController(UserAddressService userAddressService, UserAccountService userAccountService) {
        this.userAddressService = userAddressService;
        this.userAccountService = userAccountService;
    }

    @Operation(summary = "Create a new address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created",
                    content = @Content(schema = @Schema(implementation = UserAddressResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/create/{userAccountId}")
    public ResponseEntity<UserAddressResponse> save(@PathVariable long userAccountId, @RequestBody UserAddressSaveRequest request) {
        final UserAccount userAccount = userAccountService.findByIdInternal(userAccountId);

        final UserAddressResponse response = userAddressService.save(userAccount, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Edit an existing address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address edited",
                    content = @Content(schema = @Schema(implementation = UserAddressResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<UserAddressResponse> edit(@PathVariable long id, @RequestBody UserAddressEditRequest request) {
        final UserAddressResponse response = userAddressService.edit(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get all user addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All user addresses"),
    })
    @GetMapping("/get-all/{userAccountId}")
    public ResponseEntity<List<UserAddressResponse>> findAllByUserAccountId(@PathVariable long userAccountId) {
        return ResponseEntity.ok(userAddressService.findAllByUserAccountId(userAccountId));
    }

    @Operation(summary = "Get address by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user"
            ),
            @ApiResponse(responseCode = "404", description = "Address not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserAddressResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userAddressService.findById(id));
    }
}
