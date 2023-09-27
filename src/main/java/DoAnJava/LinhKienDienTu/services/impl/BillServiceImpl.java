package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Bill;
import DoAnJava.LinhKienDienTu.entity.User;
import DoAnJava.LinhKienDienTu.repository.BillRepository;
import DoAnJava.LinhKienDienTu.services.BillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAll();
    }

    @Override
    public void createBill(Bill bill, User user) {
        bill.setUser(user);
        bill.setTotalPrice(BigDecimal.valueOf(0));
        bill.setCreatedAt(LocalDate.now());
        billRepository.save(bill);
    }

    @Override
    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public Map<Integer, BigDecimal> thongKeTongTienTheoThang(int year) {
        List<Object[]> results = billRepository.getMonthlyRevenue(year);
        return convertResultsToRevenueByMonthMap(results);
    }

    @Override
    public Map<Integer, BigDecimal> thongKeTongTienTheoNgay(int month, int year) {
        List<Object[]> results = billRepository.getDailyRevenue(month, year);
        return convertResultsToRevenueByMonthMap(results);
    }

    private Map<Integer, BigDecimal> convertResultsToRevenueByMonthMap(List<Object[]> results) {
        Map<Integer, BigDecimal> revenue = new HashMap<>();
        for (Object[] result : results) {
            int num = (int) result[0];
            BigDecimal total = (BigDecimal) result[1];
            revenue.put(num, total);
        }

        return revenue;
    }
}
