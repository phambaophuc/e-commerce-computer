package DoAnJava.LinhKienDienTu.daos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Long productId;
    private String productName;
    private int price;
    private String image;
    private int quantity;
}
