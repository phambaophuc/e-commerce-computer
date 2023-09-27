package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.daos.Cart;
import DoAnJava.LinhKienDienTu.daos.Item;
import DoAnJava.LinhKienDienTu.services.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_SESSION_KEY = "cart";

    @Override
    public Cart getCart(HttpSession session) {
        return Optional.ofNullable((Cart) session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    return cart;
                });
    }

    @Override
    public Page<Item> getCartPage(HttpSession session, Pageable pageable) {
        Cart cart = getCart(session);
        List<Item> cartItems = cart.getCartItems();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cartItems.size());

        List<Item> pageItems = cartItems.subList(start, end);

        return new PageImpl<>(pageItems, pageable, cartItems.size());
    }

    @Override
    public void updateCart(HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    @Override
    public void removeCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    @Override
    public int getSumQuantity(HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    @Override
    public int getSumPrice(HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
