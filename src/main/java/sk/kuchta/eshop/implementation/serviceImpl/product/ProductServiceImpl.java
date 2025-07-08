package sk.kuchta.eshop.implementation.serviceImpl.product;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import sk.kuchta.eshop.api.dto.product.request.ProductSaveRequest;
import sk.kuchta.eshop.api.dto.product.response.ProductSaveResponse;
import sk.kuchta.eshop.api.dto.user.userAccount.response.UserAccountSaveResponse;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.exception.ResourceExistInDatabase;
import sk.kuchta.eshop.api.service.product.ProductService;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productCategory.ProductCategory;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;
import sk.kuchta.eshop.implementation.entity.user.UserAccount;
import sk.kuchta.eshop.implementation.entity.user.UserAddress;
import sk.kuchta.eshop.implementation.entity.user.UserDetail;
import sk.kuchta.eshop.implementation.mapper.specification.ProductSpecificationMapper;
import sk.kuchta.eshop.implementation.repository.product.ProductRepository;

import java.util.HashSet;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductSpecificationMapper specificationMapper;

    public ProductServiceImpl(ProductRepository repository, ProductSpecificationMapper specificationMapper) {
        this.repository = repository;
        this.specificationMapper = specificationMapper;
    }

    @Override
    public ProductSaveResponse save(ProductSaveRequest request) {
        try {
            Product product = new Product(
                    request.getName(),
                    request.getDescription(),
                    request.getPrice(),
//                    new HashSet<>(),
//                    specificationMapper.mapProductSpecificationSaveRequestToProductSpecification(request.getSpecificationRequest()),
                    request.getProductCode()
            );


            ProductSpecification productSpecification = specificationMapper.mapProductSpecificationSaveRequestToProductSpecification(request.getSpecificationRequest());
            productSpecification.setProduct(product);
            product.setSpecification(productSpecification);

            ProductCategory productCategory = new ProductCategory();
            productCategory.getProducts().add(product);


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
    public Product findByIdInternal(long id) {
        return null;
    }

    @Override
    public ProductSaveResponse findById(long id) {
        return null;
    }
}
