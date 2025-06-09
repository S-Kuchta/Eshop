package sk.kuchta.eshop.api.dto.user.userDetail.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailEditNameRequest {
    private String name;
}
