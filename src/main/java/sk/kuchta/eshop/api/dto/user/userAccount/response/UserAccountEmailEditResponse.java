package sk.kuchta.eshop.api.dto.user.userAccount.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountEmailEditResponse {
    private long id;
    private String email;
    private String message;
}
