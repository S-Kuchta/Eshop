package sk.kuchta.eshop.api.service.product;

import sk.kuchta.eshop.api.dto.product.request.ProductSaveRequest;
import sk.kuchta.eshop.api.dto.product.response.ProductSaveResponse;
import sk.kuchta.eshop.implementation.entity.product.Product;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;

public interface ProductService {

    ProductSaveResponse save(ProductSaveRequest request, Category category);
    Product findByIdInternal(long id);
    ProductSaveResponse findById(long id);
}
