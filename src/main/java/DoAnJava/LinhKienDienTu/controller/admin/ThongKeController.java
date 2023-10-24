package DoAnJava.LinhKienDienTu.controller.admin;

import DoAnJava.LinhKienDienTu.services.BillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class ThongKeController {

    private BillService billService;

    @GetMapping("/thongKeTheoThang")
    public String thongKeTheoThang(Model model) {
        int year = LocalDate.now().getYear();
        Map<Integer, BigDecimal> revenueByMonth = billService.thongKeTongTienTheoThang(year);
        model.addAttribute("revenueByMonth", revenueByMonth);
        return "admin/bill/thongKeTheoThang";
    }

    @GetMapping("/thongKeTheoThang-data")
    @ResponseBody
    public Map<Integer, BigDecimal> thongKeData(@RequestParam("year") int year) {
        return billService.thongKeTongTienTheoThang(year);
    }

    @GetMapping("/thongKeTheoNgay")
    public String thongKeNgay(Model model) {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        Map<Integer, BigDecimal> revenueByDay = billService.thongKeTongTienTheoNgay(month, year);
        model.addAttribute("revenueByDay", revenueByDay);
        return "admin/bill/thongKeTheoNgay";
    }

    @GetMapping("/thongKeTheoNgay-data")
    @ResponseBody
    public Map<Integer, BigDecimal> thongKeNgayData(@RequestParam("month") int month,
                                                    @RequestParam("year") int year) {
        return billService.thongKeTongTienTheoNgay(month, year);
    }
}
