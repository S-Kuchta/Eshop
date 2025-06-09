package sk.kuchta.eshop.api.dto.user.userAccount.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailSaveRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountSaveRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    private UserDetailSaveRequest userDetail;
    private UserAddressSaveRequest userAddress;
}
