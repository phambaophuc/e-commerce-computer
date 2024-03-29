package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Category;
import DoAnJava.LinhKienDienTu.repository.CategoryRepository;
import DoAnJava.LinhKienDienTu.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    @Cacheable(cacheNames = "category")
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
