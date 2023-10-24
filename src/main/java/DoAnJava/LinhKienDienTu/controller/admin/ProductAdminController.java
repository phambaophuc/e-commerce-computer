package DoAnJava.LinhKienDienTu.controller.admin;

import DoAnJava.LinhKienDienTu.entity.Product;
import DoAnJava.LinhKienDienTu.services.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class ProductAdminController {

    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping()
    public String getAllProduct(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/list-product";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "admin/product/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult, Model model,
                             @RequestParam(value = "mainImage") MultipartFile mainMultipartFile,
                             @RequestParam(value = "extraImage", required = false)MultipartFile[] extraMultipartFile) {

        if (bindingResult.hasErrors())
        {
            model.addAttribute("categories", categoryService.getAllCategory());
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
            {
                model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
            }
            return "admin/product/add-product";
        }

        productService.uploadFileAWS(product, mainMultipartFile, extraMultipartFile, false);

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/edit-product";
    }

    @PostMapping("/edit-product")
    public String editProduct(@ModelAttribute("product") Product product,
                              BindingResult bindingResult, Model model,
                              @RequestParam(value = "mainImage", required = false)MultipartFile mainMultipartFile,
                              @RequestParam(value = "extraImage", required = false)MultipartFile[] extraMultipartFile) {

        if (bindingResult.hasErrors())
        {
            model.addAttribute("categories", categoryService.getAllCategory());
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors)
            {
                model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
            }
            return "admin/product/edit-product" + product.getProductId();
        }

        productService.uploadFileAWS(product, mainMultipartFile, extraMultipartFile, true);

        productService.saveProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
