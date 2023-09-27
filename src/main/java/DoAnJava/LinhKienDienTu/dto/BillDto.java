package DoAnJava.LinhKienDienTu.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDto {
    private Long billId;
    private BigDecimal totalPrice;
    private LocalDate createdAt;
    private String name;
}
