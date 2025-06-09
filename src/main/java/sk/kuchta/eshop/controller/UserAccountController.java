package sk.kuchta.eshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountEmailEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountPasswordEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountEmailEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountPasswordEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.api.service.user.UserAccountService;

import java.util.List;

@RestController
@RequestMapping("user-account")
public class UserAccountController {

    //    private final AuthenticationManager authenticationManager;
    private final UserAccountService userAccountService;

    public UserAccountController(/*AuthenticationManager authenticationManager,*/ UserAccountService userAccountService) {
//        this.authenticationManager = authenticationManager;
        this.userAccountService = userAccountService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> login(@RequestBody UserAccountRequest loginRequest) {
//        Authentication authenticationRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(), loginRequest.getPassword());
//        Authentication authenticationResponse =
//                this.authenticationManager.authenticate(authenticationRequest);
//
//
//        return ResponseEntity.ok().build();
//    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(schema = @Schema(implementation = UserAccountSaveResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<UserAccountSaveResponse> save(@RequestBody UserAccountSaveRequest request) {
        UserAccountSaveResponse response = userAccountService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get user account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user"
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserAccountResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountService.findById(id));
    }

    @Operation(summary = "Get all users account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All users"),
    })
    @GetMapping("/get-all")
    public ResponseEntity<List<UserAccountResponse>> findAll() {
        return ResponseEntity.ok(userAccountService.findAll());
    }


    @Operation(summary = "Edit user email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email edited",
                    content = @Content(schema = @Schema(implementation = UserAccountEmailEditResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PutMapping("/edit-email/{id}")
    public ResponseEntity<UserAccountEmailEditResponse> editEmail(@PathVariable Long id, @RequestBody UserAccountEmailEditRequest request) {
        UserAccountEmailEditResponse response = userAccountService.editEmail(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Edit user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully",
                    content = @Content(schema = @Schema(implementation = UserAccountPasswordEditResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid old Password",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "New password same as old",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @PutMapping("/edit-password/{id}")
    public ResponseEntity<UserAccountPasswordEditResponse> editPassword(@PathVariable Long id, @Valid @RequestBody UserAccountPasswordEditRequest request) {
        UserAccountPasswordEditResponse response = userAccountService.editPassword(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Edit user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userAccountService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
