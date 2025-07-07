package sk.kuchta.eshop.implementation.serviceImpl.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressEditRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.request.UserAddressSaveRequest;
import sk.kuchta.eshop.api.dto.user.userAddress.response.UserAddressResponse;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.api.service.user.UserAddressService;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;
import sk.kuchta.eshop.implementation.mapper.user.UserAddressMapper;
import sk.kuchta.eshop.implementation.repository.user.UserAddressRepository;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);

    private final UserAddressMapper userAddressMapper;

    public UserAddressServiceImpl(UserAddressRepository repository, UserAddressMapper userAddressMapper) {
        this.repository = repository;
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    public UserAddressResponse save(UserAccount userAccount, UserAddressSaveRequest request) {
        ensureSingleBillingAddress(userAccount, request.isBillingAddress());

        try {
            long id = repository.save(
                    userAddressMapper.mapUserAddressSaveRequestToUserAddress(request)
            ).getId();

            return new UserAddressResponse(
                    id,
                    request.getStreet(),
                    request.getCity(),
                    request.getCountry(),
                    request.getZip(),
                    request.isBillingAddress());
        } catch (DataAccessException e) {
            logger.error("Error while creating user address", e);
            throw new InternalErrorException("Error while creating user address");
        }
    }

    @Override
    public UserAddressResponse edit(long id, UserAddressEditRequest request) {

        final UserAddress userAddress = findByIdInternal(id);
        final UserAccount userAccount = userAddress.getUserAccount();
        if (userAccount == null) {
            throw new ResourceNotFoundException("User not found");
        }

        ensureSingleBillingAddress(userAccount, request.isBillingAddress());

        userAddress.setStreet(request.getStreet());
        userAddress.setCity(request.getCity());
        userAddress.setCountry(request.getCountry());
        userAddress.setZip(request.getZip());
        userAddress.setBillingAddress(request.isBillingAddress());

        try {
            repository.save(userAddress);
            return userAddressMapper.mapUserAddressToResponse(userAddress);
        } catch (DataAccessException e) {
            logger.error("Error while editing user address", e);
            throw new InternalErrorException("Error while editing user address");
        }
    }

    @Override
    public UserAddress findByIdInternal(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User address with id " + id + " not found"));
    }

    @Override
    public UserAddressResponse findById(long id) {
        return userAddressMapper.mapUserAddressToResponse(findByIdInternal(id));
    }

    @Override
    public List<UserAddressResponse> findAllByUserAccountId(long userAccountId) {
        return userAddressMapper.mapUserAddressToResponse(repository.findAllByUserAccountId(userAccountId));
    }

    private void ensureSingleBillingAddress(UserAccount userAccount, boolean billingAddress) {
        if (!billingAddress || userAccount.getUserAddress().isEmpty()) {
            return;
        }

        userAccount.getUserAddress().stream()
                .filter(UserAddress::isBillingAddress)
                .forEach(userAddress -> userAddress.setBillingAddress(false));
    }
}
