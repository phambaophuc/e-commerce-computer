package DoAnJava.LinhKienDienTu.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class HomeAdminController {

    @GetMapping
    public String index() {
        return "admin/index";
    }

}
