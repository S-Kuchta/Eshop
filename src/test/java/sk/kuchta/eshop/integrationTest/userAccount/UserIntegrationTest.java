package sk.kuchta.eshop.integrationTest.userAccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountEmailEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountPasswordEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountEmailEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountPasswordEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailSaveRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;
import sk.kuchta.eshop.api.exception.response.ApiErrorResponse;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource("classpath:application-test.properties")
public class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldSaveUserAccountSuccessfully() {
        saveUserAccount();
    }

    @Test
    public void saveUserAccountEmailDuplicateException() {
        final String email = "duplicate@email.com";
        saveUserAccount(email, "password", "0000000");
        final ResponseEntity<String> response = saveUserAccountExpectingError(email, "password", HttpStatus.CONFLICT);

        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().contains("User with email " + email + " already exists"));
    }

    @Test
    public void findUserById() {
        final UserAccountSaveResponse userAccountSaveResponse = saveUserAccount();

        final ResponseEntity<UserAccountResponse> response = restTemplate.getForEntity(
                "/user-account/get-by-id/" + userAccountSaveResponse.getUserAccountResponse().getId(),
                UserAccountResponse.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userAccountSaveResponse.getUserAccountResponse().getId(), response.getBody().getId());
        Assertions.assertEquals(userAccountSaveResponse.getUserAccountResponse().getEmail(), response.getBody().getEmail());
    }

    @Test
    public void shouldReturn404WhenUserNotFound() {
        final long id = 4;
        final ResponseEntity<ApiErrorResponse> response = restTemplate.getForEntity(
                "/user-account/get-by-id/" + id,
                ApiErrorResponse.class
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(404, response.getBody().getStatus());
        Assertions.assertEquals("User with id " + id + " not found", response.getBody().getMessage());
    }

    @Test
    public void findAll() {
        saveUserAccount("email@mail.com", "password", "000000");
        saveUserAccount("email1@mail.com", "password", "111111");
        saveUserAccount("email2@mail.com", "password", "222222");
        saveUserAccount("email3@mail.com", "password", "333333");

        final ResponseEntity<List<UserAccountResponse>> response = restTemplate.exchange(
                "/user-account/get-all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(4, response.getBody().size());
    }

    @Test
    public void editEmail() {
        final String oldEmail = "email@mail.com";
        final UserAccountSaveResponse accountSaveResponse = saveUserAccount(oldEmail, "password", "000000");
        Assertions.assertEquals(oldEmail, accountSaveResponse.getUserAccountResponse().getEmail());

        String newEmail = "newEmail@mail.com";
        final UserAccountEmailEditRequest editRequest = new UserAccountEmailEditRequest(newEmail);
        final HttpEntity<UserAccountEmailEditRequest> request = new HttpEntity<>(editRequest);

        ResponseEntity<UserAccountEmailEditResponse> response = restTemplate.exchange(
                "/user-account/edit-email/" + accountSaveResponse.getUserAccountResponse().getId(),
                HttpMethod.PUT,
                request,
                UserAccountEmailEditResponse.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(newEmail, response.getBody().getEmail());
        Assertions.assertEquals("Email edited successfully. New email is: " + newEmail, response.getBody().getMessage());

    }

    @Test
    public void shouldReturnConflictWhenEmailAlreadyExists() {
        final String oldEmail = "email@mail.com";
        final UserAccountSaveResponse accountSaveResponse = saveUserAccount(oldEmail, "password", "000000");
        Assertions.assertEquals(oldEmail, accountSaveResponse.getUserAccountResponse().getEmail());

        final String newEmail = "newEmail@mail.com";
        saveUserAccount(newEmail, "password", "111111");

        final UserAccountEmailEditRequest editRequest = new UserAccountEmailEditRequest(newEmail);
        final HttpEntity<UserAccountEmailEditRequest> request = new HttpEntity<>(editRequest);

        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(
                "/user-account/edit-email/" + accountSaveResponse.getUserAccountResponse().getId(),
                HttpMethod.PUT,
                request,
                ApiErrorResponse.class
        );

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(409, response.getBody().getStatus());
        Assertions.assertEquals("User with email " + newEmail + " already exists", response.getBody().getMessage());
    }

    @Test
    public void editPassword() {
        ResponseEntity<UserAccountPasswordEditResponse> response = passwordChangeRequest("password", "newPassword", "00000");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Password changed successfully", response.getBody().getMessage());
    }

    @Test
    public void editPasswordSameAsOldPassword() {
        ResponseEntity<ApiErrorResponse> response = passwordChangeBadRequest("password", "password", "000000");

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(409, response.getStatusCode().value());
        Assertions.assertEquals("New password can not be the same as old password", response.getBody().getMessage());
    }

    @Test
    public void deleteUserAccount() {
        final UserAccountSaveResponse userAccount = saveUserAccount();
        final long id = userAccount.getUserAccountResponse().getId();
        final long userAddressId = userAccount.getUserAccountResponse().getAddresses().getFirst().getId();
        final long userDetailId = userAccount.getUserAccountResponse().getUserDetailResponse().getId();
        Assertions.assertNotNull(userAccount);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/user-account/delete/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        ResponseEntity<ApiErrorResponse> notFoundResponse = restTemplate.getForEntity(
                "/user-account/get-by-id/" + id,
                ApiErrorResponse.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());

        ResponseEntity<ApiErrorResponse> notFoundResponseAddress = restTemplate.getForEntity(
                "/user-address/get-by-id/" + userAddressId,
                ApiErrorResponse.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundResponseAddress.getStatusCode());

        ResponseEntity<ApiErrorResponse> notFoundResponseDetail = restTemplate.getForEntity(
                "/user-detail/get-by-id/" + userDetailId,
                ApiErrorResponse.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundResponseDetail.getStatusCode());
    }

    public ResponseEntity<UserAccountPasswordEditResponse> passwordChangeRequest(String oldPassword, String newPassword, String phoneNumber) {
        final UserAccountSaveResponse user = saveUserAccount("email@mail.com", "password", phoneNumber);

        final UserAccountPasswordEditRequest editRequest = new UserAccountPasswordEditRequest(oldPassword, newPassword);
        final HttpEntity<UserAccountPasswordEditRequest> request = new HttpEntity<>(editRequest);

        ResponseEntity<UserAccountPasswordEditResponse> response = restTemplate.exchange(
                "/user-account/edit-password/" + user.getUserAccountResponse().getId(),
                HttpMethod.PUT,
                request,
                UserAccountPasswordEditResponse.class
        );

        Assertions.assertNotNull(response.getBody());

        return response;
    }

    public ResponseEntity<ApiErrorResponse> passwordChangeBadRequest(String oldPassword, String newPassword, String phoneNumber) {
        final UserAccountSaveResponse user = saveUserAccount("email@mail.com", "password", phoneNumber);

        final UserAccountPasswordEditRequest editRequest = new UserAccountPasswordEditRequest(oldPassword, newPassword);
        final HttpEntity<UserAccountPasswordEditRequest> request = new HttpEntity<>(editRequest);

        ResponseEntity<ApiErrorResponse> response = restTemplate.exchange(
                "/user-account/edit-password/" + user.getUserAccountResponse().getId(),
                HttpMethod.PUT,
                request,
                ApiErrorResponse.class
        );

        Assertions.assertNotNull(response.getBody());

        return response;
    }

    public UserAccountSaveResponse saveUserAccount(String email, String password, String phoneNumber) {
        UserAddressSaveRequest address = new UserAddressSaveRequest("street", "city", "country", 0, true);
        UserDetailSaveRequest detail = createUserDetail("name", phoneNumber);
        final UserAccountSaveRequest userAccount = new UserAccountSaveRequest(email, password, detail, address);

        final HttpEntity<UserAccountSaveRequest> request = new HttpEntity<>(userAccount);

        ResponseEntity<UserAccountSaveResponse> responseEntity = restTemplate.exchange(
                "/user-account/create",
                HttpMethod.POST,
                request,
                UserAccountSaveResponse.class
        );

        final UserAccountSaveResponse responseBody = responseEntity.getBody();
        Assertions.assertNotNull(responseEntity.getBody());
        final UserDetailResponse detailResponse = responseEntity.getBody().getUserAccountResponse().getUserDetailResponse();
        Assertions.assertEquals(detail.getName(), detailResponse.getName());
        Assertions.assertEquals(detail.getPhoneNumber(), detailResponse.getPhoneNumber());


        Assertions.assertNotNull(responseBody);
        final List<UserAddressResponse> addressResponse = responseBody.getUserAccountResponse().getAddresses();
        Assertions.assertEquals(1, addressResponse.size());
        Assertions.assertEquals(address.getStreet(), addressResponse.getFirst().getStreet());
        Assertions.assertEquals(address.getCity(), addressResponse.getFirst().getCity());
        Assertions.assertEquals(address.getCountry(), addressResponse.getFirst().getCountry());
        Assertions.assertEquals(address.isBillingAddress(), addressResponse.getFirst().isBillingAddress());

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(userAccount.getEmail(), responseBody.getUserAccountResponse().getEmail());

        return responseBody;
    }

    public ResponseEntity<String> saveUserAccountExpectingError(String email, String password, HttpStatus expectedStatus) {
        UserAddressSaveRequest address = new UserAddressSaveRequest("street", "city", "country", 0, true);
        UserDetailSaveRequest detail = createUserDetail("name", "090000");
        final UserAccountSaveRequest userAccount = new UserAccountSaveRequest(email, password, detail, address);
        final HttpEntity<UserAccountSaveRequest> request = new HttpEntity<>(userAccount);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/user-account/create",
                HttpMethod.POST,
                request,
                String.class
        );

        Assertions.assertEquals(expectedStatus, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        return responseEntity;
    }

    public UserAccountSaveResponse saveUserAccount() {
        return saveUserAccount("email@mail.com", "password", "000000");
    }

    private UserDetailSaveRequest createUserDetail(String name, String phoneNumber) {
        UserDetailSaveRequest saveRequest = new UserDetailSaveRequest(name, phoneNumber);

        Assertions.assertNotNull(saveRequest);
        Assertions.assertEquals(name, saveRequest.getName());
        Assertions.assertEquals(phoneNumber, saveRequest.getPhoneNumber());

        return saveRequest;
    }


}
