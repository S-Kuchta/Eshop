package sk.kuchta.eshop.api.service.category;

import sk.kuchta.eshop.implementation.entity.productCategory.Category;

public interface CategoryService {

    Category findByIdInternal(long id);
}
