package sk.kuchta.eshop.api.service.product;

import sk.kuchta.eshop.api.dto.product.request.ProductSaveRequest;
import sk.kuchta.eshop.api.dto.product.response.ProductSaveResponse;
import sk.kuchta.eshop.implementation.entity.product.Product;

public interface ProductService {

    ProductSaveResponse save(ProductSaveRequest request);
    Product findByIdInternal(long id);
    ProductSaveResponse findById(long id);
}
