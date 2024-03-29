package DoAnJava.LinhKienDienTu.controller;

import DoAnJava.LinhKienDienTu.entity.*;
import DoAnJava.LinhKienDienTu.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private CommentService commentService;
    private UserService userService;
    private HtmlService htmlService;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "12") int pageSize) {
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        model.addAttribute("categories", categoryService.getAllCategory());
        return "product/list";
    }

    @GetMapping("/{productId}")
    public String detailProduct(@PathVariable Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        model.addAttribute("currentUsername", currentUsername);

        Product product = productService.getProductById(productId);
        String htmlDescription = product.getDescription();
        model.addAttribute("description", htmlService.markdownToHtml(htmlDescription));

        model.addAttribute("product", product);

        List<Comment> comments = commentService.getCommentByProductId(productId);
        model.addAttribute("comments", comments);

        Comment newComment = new Comment();
        model.addAttribute("newComment", newComment);

        return "product/detail";
    }

    @PostMapping("/{productId}")
    public String addComment(@PathVariable Long productId, @ModelAttribute Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Product product = productService.getProductById(productId);

        comment.setProduct(product);
        comment.setUser(userService.getUserByUsername(username));

        commentService.saveComment(comment);

        return "redirect:/product/{productId}";
    }

    @GetMapping("/delete-comment/{commentId}")
    public String deleteComment(@PathVariable UUID commentId, HttpServletRequest request) {
        String previoustPage = request.getHeader("Referer");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Comment comment = commentService.getCommentById(commentId);
        if (comment.getUser().getUsername().equals(username)) {
            commentService.removeComment(commentId);
        }

        return "redirect:" + previoustPage;
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam(value = "name", required = false) String productName, Model model,
                                @RequestParam(value = "category", required = false) String categoryName) {
        if (productName != null && categoryName == null) {
            List<Product> products = productService.getProductByName(productName);
            model.addAttribute("products", products);
            model.addAttribute("searchString", productName);
            return "product/list-search";
        } else {
            List<Product> products = productService.getProductByCategory(categoryName);
            model.addAttribute("products", products);
            model.addAttribute("searchString", categoryName);
        }
        return "product/list-search";
    }

}
