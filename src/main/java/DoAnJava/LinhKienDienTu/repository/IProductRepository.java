package DoAnJava.LinhKienDienTu.repository;

import DoAnJava.LinhKienDienTu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName LIKE CONCAT('%',:query,'%')")
    List<Product> findProductByName(String query);

    @Query("SELECT p FROM Product p WHERE p.category.categoryName LIKE CONCAT('%',:query,'%')")
    List<Product> findProductByCategory(String query);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :query")
    List<Product> findProductByCategoryId(Long query);
}
