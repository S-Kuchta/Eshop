package sk.kuchta.eshop.api.dto.user.userAccount.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponse {
    private long id;
    private String email;
    private List<UserAddressResponse> addresses;
    private UserDetailResponse userDetailResponse;
}
