package sk.kuchta.eshop.implementation.mapper.user;

import org.springframework.stereotype.Component;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailSaveRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;

@Component
public class UserDetailMapper {

    public UserDetailResponse mapUserDetailToResponse(UserDetail userDetail) {
        return new UserDetailResponse(userDetail.getId(), userDetail.getName(), userDetail.getPhoneNumber());
    }

    public UserDetail mapUserDetailToUser(UserDetailSaveRequest request) {
        return new UserDetail(
                request.getName(),
                request.getPhoneNumber()
        );
    }

}
