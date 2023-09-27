package DoAnJava.LinhKienDienTu.controller.api;

import DoAnJava.LinhKienDienTu.dto.ProductDto;
import DoAnJava.LinhKienDienTu.entity.Product;
import DoAnJava.LinhKienDienTu.mapper.ProductMapper;
import DoAnJava.LinhKienDienTu.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/build")
@AllArgsConstructor
public class BuildApiController {

    private ProductService productService;
    private ProductMapper productMapper;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable("id") Long categoryId) {
        List<Product> products = productService.getProductByCategoryId(categoryId);
        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDtos);
    }

}
