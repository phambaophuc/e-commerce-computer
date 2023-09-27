package DoAnJava.LinhKienDienTu.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private Long categoryId;
    private String productName;
    private String image;
    private int price;
}
