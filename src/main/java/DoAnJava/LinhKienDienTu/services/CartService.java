package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.daos.Cart;
import DoAnJava.LinhKienDienTu.daos.Item;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartService {
    
    Cart getCart(@NotNull HttpSession session);

    Page<Item> getCartPage(HttpSession session, Pageable pageable);
    
    void updateCart(@NotNull HttpSession session, Cart cart);
    
    void removeCart(@NotNull HttpSession session);
    
    int getSumQuantity(@NotNull HttpSession session);
    
    int getSumPrice(@NotNull HttpSession session);
}
