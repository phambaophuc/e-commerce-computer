package DoAnJava.LinhKienDienTu.controller.api;

import DoAnJava.LinhKienDienTu.services.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartApi {

    private CartService cartService;

    @GetMapping("/cartItemCount")
    public ResponseEntity<Integer> getCartItemCount(HttpSession session) {
        int count = cartService.getSumQuantity(session);
        return ResponseEntity.ok(count);
    }
}
