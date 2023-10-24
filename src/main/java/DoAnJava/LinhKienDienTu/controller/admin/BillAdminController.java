package DoAnJava.LinhKienDienTu.controller.admin;

import DoAnJava.LinhKienDienTu.dto.BillDto;
import DoAnJava.LinhKienDienTu.entity.Bill;
import DoAnJava.LinhKienDienTu.mapper.BillMapper;
import DoAnJava.LinhKienDienTu.services.BillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/bills")
@AllArgsConstructor
public class BillAdminController {

    private BillService billService;
    private BillMapper billMapper;

    @GetMapping()
    public String getAllBills(Model model) {
        List<Bill> bills = billService.getAllBill();
        List<BillDto> billDtos = bills.stream()
                .map(billMapper::toBillDto)
                .collect(Collectors.toList());

        model.addAttribute("bills", billDtos);
        return "admin/bill/list-bill";
    }
}
