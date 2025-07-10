package sk.kuchta.eshop.implementation.serviceImpl.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.dto.product.ProductDTO;
import sk.kuchta.eshop.api.dto.product.request.ProductSaveRequest;
import sk.kuchta.eshop.api.dto.product.response.ProductSaveResponse;
import sk.kuchta.eshop.api.exception.InternalErrorException;
import sk.kuchta.eshop.api.service.product.ProductService;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;
import sk.kuchta.eshop.implementation.mapper.product.ProductMapper;
import sk.kuchta.eshop.implementation.mapper.specification.ProductSpecificationMapper;
import sk.kuchta.eshop.implementation.repository.product.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductSpecificationMapper specificationMapper;
    private final ProductMapper productMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository repository, ProductSpecificationMapper specificationMapper, ProductMapper productMapper) {
        this.repository = repository;
        this.specificationMapper = specificationMapper;
        this.productMapper = productMapper;
    }

    @Override
    public ProductSaveResponse save(ProductSaveRequest request, Category category) {
        final ProductDTO productDTO = request.getProductDTO();

        try {
            Product product = new Product(
                    productDTO.getName(),
                    productDTO.getDescription(),
                    productDTO.getPrice(),
                    productDTO.getProductCode()
            );

            final ProductSpecification productSpecification = specificationMapper.mapToProductSpecification(productDTO.getProductSpecificationDTO());
            productSpecification.setProduct(product);
            product.setSpecification(productSpecification);

            // TODO category
            product.getCategories().add(category);
            category.getProducts().add(product);
//            final Category category = new Category();
//            category.getProducts().add(product);

            Product savedProduct = repository.save(product);

            return new ProductSaveResponse(
                    "Product created successfully",
                    productMapper.toProductDTO(savedProduct)
            );
        } catch (DataAccessException e) {
            logger.error("Error while creating product", e);
            throw new InternalErrorException("Error while creating product");
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
