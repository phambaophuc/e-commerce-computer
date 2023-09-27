package DoAnJava.LinhKienDienTu.mapper;

import DoAnJava.LinhKienDienTu.dto.BillDto;
import DoAnJava.LinhKienDienTu.entity.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {

    public BillDto toBillDto(Bill bill) {
        return BillDto.builder()
                .billId(bill.getBillId())
                .totalPrice(bill.getTotalPrice())
                .createdAt(bill.getCreatedAt())
                .name(bill.getUser().getFullname())
                .build();
    }
}
