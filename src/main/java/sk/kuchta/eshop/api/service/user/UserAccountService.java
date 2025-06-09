package sk.kuchta.eshop.api.service.user;

import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountEmailEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountPasswordEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountEmailEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountPasswordEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccountSaveResponse save(UserAccountSaveRequest request);
    UserAccount findByIdInternal(long id);
    UserAccountResponse findById(long id);
    List<UserAccountResponse> findAll();
    UserAccountEmailEditResponse editEmail(long id, UserAccountEmailEditRequest request);
    UserAccountPasswordEditResponse editPassword(long id, UserAccountPasswordEditRequest request);
    void deleteById(long id);

}
