package DoAnJava.LinhKienDienTu.mapper;

import DoAnJava.LinhKienDienTu.dto.ProductDto;
import DoAnJava.LinhKienDienTu.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper{

    public ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .categoryId(product.getCategory().getCategoryId())
                .productName(product.getProductName())
                .image(product.getMainImage())
                .price(product.getPrice())
                .build();
    }
}
