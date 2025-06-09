package sk.kuchta.eshop.api.dto.user.userAccount.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAccountSaveResponse {

    private String message;
    private UserAccountResponse userAccountResponse;

}
