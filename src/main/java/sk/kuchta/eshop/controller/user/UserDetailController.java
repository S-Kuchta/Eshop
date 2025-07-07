package sk.kuchta.eshop.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditNameRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditTelephoneNumberRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.api.service.user.UserAccountService;
import sk.kuchta.eshop.api.service.user.UserDetailService;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;

@RestController
@RequestMapping("user-detail")
public class UserDetailController {

    private final UserDetailService userDetailService;
    private final UserAccountService userAccountService;

    public UserDetailController(UserDetailService userDetailService, UserAccountService userAccountService) {
        this.userDetailService = userDetailService;
        this.userAccountService = userAccountService;
    }

    @Operation(summary = "Edit user email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name edited",
                    content = @Content(schema = @Schema(implementation = UserAccountSaveResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PutMapping("/edit-name/{id}")
    public ResponseEntity<String> editName(@PathVariable Long id, @RequestBody UserDetailEditNameRequest request) {
        final UserAccount userAccount = userAccountService.findByIdInternal(id);
        String response = userDetailService.editName(userAccount, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Edit user phone number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email edited",
                    content = @Content(schema = @Schema(implementation = UserAccountSaveResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Phone Number already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PutMapping("/edit-phoneNumber/{id}")
    public ResponseEntity<String> editPhoneNumber(@PathVariable Long id, @RequestBody UserDetailEditTelephoneNumberRequest request) {
        final UserAccount userAccount = userAccountService.findByIdInternal(id);
        String response = userDetailService.editPhoneNumber(userAccount, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user detail"
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserDetailResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userDetailService.findById(id));
    }

}
