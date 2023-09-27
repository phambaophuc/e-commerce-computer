package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Bill;
import DoAnJava.LinhKienDienTu.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BillService {

    List<Bill> getAllBill();

    void createBill(Bill bill, User user);

    void updateBill(Bill bill);

    Map<Integer, BigDecimal> thongKeTongTienTheoThang(int year);

    Map<Integer, BigDecimal> thongKeTongTienTheoNgay(int month, int year);

}
