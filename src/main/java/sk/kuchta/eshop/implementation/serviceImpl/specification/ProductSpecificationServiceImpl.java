package sk.kuchta.eshop.implementation.serviceImpl.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.dto.specification.request.ProductSpecificationRequest;
import sk.kuchta.eshop.api.dto.specification.response.ProductSpecificationResponse;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.api.service.specification.ProductSpecificationService;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;
import sk.kuchta.eshop.implementation.mapper.specification.ProductSpecificationMapper;
import sk.kuchta.eshop.implementation.repository.specification.ProductSpecificationRepository;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

    private final ProductSpecificationRepository repository;
    private final ProductSpecificationMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductSpecificationServiceImpl.class);

    public ProductSpecificationServiceImpl(ProductSpecificationRepository repository, ProductSpecificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductSpecificationResponse save(ProductSpecificationRequest request, Product product) {
        try {
            long id = repository.save(
                    mapper.mapToProductSpecification(request.getProductSpecificationDTO(), product)
            ).getId();

            return new ProductSpecificationResponse(request.getProductSpecificationDTO());
        } catch (DataAccessException e) {
            logger.error("Error while creating product specification", e);
            throw new InternalErrorException("Error while creating product specification");
        }
    }

    @Override
    public ProductSpecification findByIdInternal(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specification with id " + id + " not found"));
    }

    @Override
    public ProductSpecificationResponse findById(long id) {
        return new ProductSpecificationResponse(mapper.mapToProductSpecificationDTO(findByIdInternal(id)));
    }
}
