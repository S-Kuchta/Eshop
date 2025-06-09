package sk.kuchta.eshop.api.dto.user.userAddress.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressEditRequest {
    private String street;
    private String city;
    private String country;
    private int zip;
    private boolean billingAddress;
}
