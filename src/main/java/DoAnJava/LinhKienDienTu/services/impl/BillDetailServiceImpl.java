package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.repository.BillDetailRepository;
import DoAnJava.LinhKienDienTu.services.BillDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {

    private BillDetailRepository billDetailRepository;

    @Override
    public void addProductToBill(Long productId, Long billId, int quantity) {
        billDetailRepository.addProductToBill(productId, billId, quantity);
    }
}
