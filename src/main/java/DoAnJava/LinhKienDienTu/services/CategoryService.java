package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Category;
import DoAnJava.LinhKienDienTu.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
