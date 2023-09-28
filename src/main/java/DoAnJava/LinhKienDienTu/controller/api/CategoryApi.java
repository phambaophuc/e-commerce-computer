package DoAnJava.LinhKienDienTu.controller.api;

import DoAnJava.LinhKienDienTu.dto.CategoryDto;
import DoAnJava.LinhKienDienTu.entity.Category;
import DoAnJava.LinhKienDienTu.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryApi {

    private CategoryService categoryService;

    @GetMapping()
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryService.getAllCategory();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        categories.forEach(c -> categoryDtos.add(toDto(c)));

        return categoryDtos;
    }

    public CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .categoryName(category.getCategoryName())
                .build();
    }
}
