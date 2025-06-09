package sk.kuchta.eshop.implementation.mapper.user;

import org.springframework.stereotype.Component;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountResponse;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;

import java.util.List;

@Component
public class UserAccountMapper {

    private final UserDetailMapper userDetailMapper;
    private final UserAddressMapper userAddressMapper;

    public UserAccountMapper(UserDetailMapper userDetailMapper, UserAddressMapper userAddressMapper) {
        this.userDetailMapper = userDetailMapper;
        this.userAddressMapper = userAddressMapper;
    }

    public UserAccountResponse mapUserAccountToResponse(UserAccount userAccount) {
        return new UserAccountResponse(
                userAccount.getId(),
                userAccount.getEmail(),
                userAddressMapper.mapUserAddressToResponse(userAccount.getUserAddress()),
                userDetailMapper.mapUserDetailToResponse(userAccount.getUserDetail())
        );
    }

    public UserAccount mapUserResponseToUserAccount(UserAccountSaveRequest request) {
        return new UserAccount(
                request.getEmail(),
                request.getPassword(),
                userAddressMapper.mapUserAddressRequestToUserAddresses(List.of(request.getUserAddress())),
                userDetailMapper.mapUserDetailToUser(request.getUserDetail())
        );
    }
}
