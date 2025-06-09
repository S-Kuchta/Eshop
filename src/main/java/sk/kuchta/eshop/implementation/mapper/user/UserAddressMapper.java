package sk.kuchta.eshop.implementation.mapper.user;

import org.springframework.stereotype.Component;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;

import java.util.List;

@Component
public class UserAddressMapper {

    public UserAddressResponse mapUserAddressToResponse(UserAddress userAddress) {
        return new UserAddressResponse(userAddress.getId(),
                userAddress.getStreet(),
                userAddress.getCity(),
                userAddress.getCountry(),
                userAddress.getZip(),
                userAddress.isBillingAddress());
    }

    public List<UserAddressResponse> mapUserAddressToResponse(List<UserAddress> userAddresses) {
        return userAddresses
                .stream()
                .map(this::mapUserAddressToResponse)
                .toList();
    }

    public UserAddress mapUserAddressSaveRequestToUserAddress(UserAddressSaveRequest request) {
        return new UserAddress(
                request.getStreet(),
                request.getCity(),
                request.getCountry(),
                request.getZip(),
                request.isBillingAddress());
    }

    public List<UserAddress> mapUserAddressRequestToUserAddresses(List<UserAddressSaveRequest> requests) {
        return requests
                .stream()
                .map(this::mapUserAddressSaveRequestToUserAddress)
                .toList();
    }
}
