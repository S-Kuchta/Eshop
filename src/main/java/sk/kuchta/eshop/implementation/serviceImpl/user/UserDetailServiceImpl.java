package sk.kuchta.eshop.implementation.serviceImpl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditNameRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailEditTelephoneNumberRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.request.UserDetailSaveRequest;
import sk.kuchta.eshop.api.dto.user.userDetail.response.UserDetailResponse;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.exception.ResourceExistInDatabase;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.api.service.user.UserDetailService;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;
import sk.kuchta.eshop.implementation.mapper.user.UserDetailMapper;
import sk.kuchta.eshop.implementation.repository.user.UserDetailRepository;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserDetailRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserDetailMapper userDetailMapper;

    public UserDetailServiceImpl(UserDetailRepository repository, UserDetailMapper userDetailMapper) {
        this.repository = repository;
        this.userDetailMapper = userDetailMapper;
    }

    @Override
    public UserDetailResponse save(UserAccount userAccount, UserDetailSaveRequest request) {
//        try {
//            long id = repository.save(new UserDetail(
//                    request.getName(),
//                    request.getPhoneNumber(),
//                    userAccount)
//            ).getId();
//
////            UserDetail userDetail = findByIdInternal(id);
//
//            return new UserDetailResponse(id, request.getName(), request.getPhoneNumber());
////            return userDetailMapper.mapUserDetailToResponse(userDetail);
//        } catch (DataIntegrityViolationException e) {
//            throw new ResourceExistInDatabase("Phone number  " + request.getPhoneNumber() + " is already used");
//        } catch (DataAccessException e) {
//            logger.error("Error while creating user details", e);
//            throw new InternalErrorException("Error while creating user details");
//        }
        return null;
    }

    @Override
    public UserDetail findByIdInternal(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User details with id " + id + " not found"));
    }

    @Override
    public UserDetailResponse findById(long id) {
        return userDetailMapper.mapUserDetailToResponse(findByIdInternal(id));
    }

    @Override
    public String editName(UserAccount userAccount, UserDetailEditNameRequest request) {
        UserDetail userDetail = userAccount.getUserDetail();
        userDetail.setName(request.getName());

        try {
            repository.save(userDetail);
            return userDetail.getName();
        } catch (DataAccessException e) {
            logger.error("Error while editing password", e);
            throw new InternalErrorException("Error while editing password");
        }
    }

    @Override
    public String editPhoneNumber(UserAccount userAccount, UserDetailEditTelephoneNumberRequest request) {
        UserDetail userDetail = userAccount.getUserDetail();
        userDetail.setPhoneNumber(request.getPhoneNumber());

        try {
            repository.save(userDetail);
            return userDetail.getPhoneNumber();
        } catch (DataIntegrityViolationException e){
            throw new ResourceExistInDatabase("Phone number " + request.getPhoneNumber() + " is already used");
        } catch (DataAccessException e) {
            logger.error("Error while editing password", e);
            throw new InternalErrorException("Error while editing password");
        }
    }
}
