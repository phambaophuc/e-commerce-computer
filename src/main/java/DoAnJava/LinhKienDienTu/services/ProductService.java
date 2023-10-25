package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Page<Product> getAllProducts(Pageable pageable);

    Product getProductById(Long id);

    List<Product> getProductByName(String productName);

    List<Product> getProductByCategory(String categoryName);

    void saveProduct(Product product);

    void deleteProduct(Long id);

}
