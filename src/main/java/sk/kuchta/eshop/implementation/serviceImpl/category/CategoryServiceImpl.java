package sk.kuchta.eshop.implementation.serviceImpl.category;

import org.springframework.stereotype.Service;
import sk.kuchta.eshop.api.exception.ResourceNotFoundException;
import sk.kuchta.eshop.api.service.category.CategoryService;
import sk.kuchta.eshop.implementation.entity.productCategory.Category;
import sk.kuchta.eshop.implementation.repository.category.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category findByIdInternal(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }
}
