package sk.kuchta.eshop.api.dto.user.userAccount.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEmailEditRequest {
    private String email;
}
