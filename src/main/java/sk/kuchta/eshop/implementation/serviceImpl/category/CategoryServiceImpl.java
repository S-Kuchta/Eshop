package sk.kuchta.eshop.implementation.serviceImpl.category;

import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.service.category.CategoryService;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Category findByIdInternal() {
        return null;
    }
}
