package sk.kuchta.eshop.implementation.serviceImpl.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountEmailEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountPasswordEditRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.request.UserAccountSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountEmailEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountPasswordEditResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.exception.BadRequestException;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.exception.ResourceExistInDatabase;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.api.service.user.UserAccountService;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;
import sk.kuchta.eshop.implementation.mapper.user.UserAccountMapper;
import sk.kuchta.eshop.implementation.mapper.user.UserAddressMapper;
import sk.kuchta.eshop.implementation.mapper.user.UserDetailMapper;
import sk.kuchta.eshop.implementation.repository.user.UserAccountRepository;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);


    private final UserAccountMapper userAccountMapper;
    private final UserDetailMapper userDetailMapper;
    private final UserAddressMapper userAddressMapper;

    public UserAccountServiceImpl(UserAccountRepository repository, PasswordEncoder passwordEncoder, UserAccountMapper userAccountMapper, UserDetailMapper userDetailMapper, UserAddressMapper userAddressMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userAccountMapper = userAccountMapper;
        this.userDetailMapper = userDetailMapper;
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    @Transactional
    public UserAccountSaveResponse save(UserAccountSaveRequest request) {
        try {
            UserAccount userAccount = new UserAccount(
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword())
            );

            UserDetail userDetail = userDetailMapper.mapUserDetailToUser(request.getUserDetail());
            userDetail.setUserAccount(userAccount);
            userAccount.setUserDetail(userDetail);

            UserAddress userAddress = userAddressMapper.mapUserAddressSaveRequestToUserAddress(request.getUserAddress());
            userAddress.setUserAccount(userAccount);
            userAccount.getUserAddress().add(userAddress);

            UserAccount savedUserAccount = repository.save(userAccount);

            return new UserAccountSaveResponse(
                    "User account created successfully",
                    userAccountMapper.mapUserAccountToResponse(savedUserAccount)
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceExistInDatabase("User with email " + request.getEmail() + " already exists");
        } catch (DataAccessException e) {
            logger.error("Error while creating user account", e);
            throw new InternalErrorException("Error while creating user account");
        }
    }

    @Override
    public UserAccount findByIdInternal(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserAccountResponse findById(long id) {
        return userAccountMapper.mapUserAccountToResponse(findByIdInternal(id));
    }

    @Override
    public List<UserAccountResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(userAccountMapper::mapUserAccountToResponse)
                .toList();
    }

    @Override
    public UserAccountEmailEditResponse editEmail(long id, UserAccountEmailEditRequest request) {
        final UserAccount userAccount = findByIdInternal(id);
        userAccount.setEmail(request.getEmail());

        try {
            repository.save(userAccount);
            return new UserAccountEmailEditResponse(
                    id,
                    request.getEmail(),
                    "Email edited successfully. New email is: " + userAccount.getEmail()
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResourceExistInDatabase("User with email " + request.getEmail() + " already exists");
        } catch (DataAccessException e) {
            logger.error("Error while editing email", e);
            throw new InternalErrorException("Error while editing email");
        }
    }

    @Override
    public UserAccountPasswordEditResponse editPassword(long id, UserAccountPasswordEditRequest request) {
        final UserAccount userAccount = findByIdInternal(id);

        if (!passwordEncoder.matches(request.getOldPassword(), userAccount.getPassword())) {
            throw new BadRequestException("Invalid old Password");
        }

        if (passwordEncoder.matches(request.getNewPassword(), userAccount.getPassword())) {
            throw new ResourceExistInDatabase("New password can not be the same as old password");
        }

        userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));

        try {
            repository.save(userAccount);
            return new UserAccountPasswordEditResponse(id, "Password changed successfully");
        } catch (DataAccessException e) {
            logger.error("Error while editing password", e);
            throw new InternalErrorException("Error while editing password");
        }
    }

    @Override
    public void deleteById(long id) {
        if (findByIdInternal(id) != null) {
            repository.deleteById(id);
        }
    }
}
