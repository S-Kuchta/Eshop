package sk.kuchta.eshop.api.service.user;

import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditNameRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditTelephoneNumberRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailSaveRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;

public interface UserDetailService {

    UserDetailResponse save(UserAccount userAccount, UserDetailSaveRequest request);
    UserDetail findByIdInternal(long id);
    UserDetailResponse findById(long id);
    String editName(UserAccount userAccount, UserDetailEditNameRequest request);
    String editPhoneNumber(UserAccount userAccount, UserDetailEditTelephoneNumberRequest request);

}
