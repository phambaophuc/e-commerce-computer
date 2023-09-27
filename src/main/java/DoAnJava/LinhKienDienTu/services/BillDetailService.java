package DoAnJava.LinhKienDienTu.services;

public interface BillDetailService {

    void addProductToBill(Long productId, Long billId, int quantity);

}
