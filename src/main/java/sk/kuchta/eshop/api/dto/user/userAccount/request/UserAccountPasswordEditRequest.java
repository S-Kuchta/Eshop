package sk.kuchta.eshop.api.dto.user.userAccount.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountPasswordEditRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
