package sk.kuchta.eshop.api.dto.user.userDetail.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private long id;
    private String name;
    private String phoneNumber;
}
