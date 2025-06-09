package sk.kuchta.eshop.api.service.user;

import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressEditRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    UserAddressResponse save(UserAccount userAccount, UserAddressSaveRequest request);

    UserAddressResponse edit(long id, UserAddressEditRequest request);

    UserAddress findByIdInternal(long id);

    UserAddressResponse findById(long id);

    List<UserAddressResponse> findAllByUserAccountId(long userAccountId);
}
