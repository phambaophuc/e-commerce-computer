package DoAnJava.LinhKienDienTu.repository;

import DoAnJava.LinhKienDienTu.entity.BillDetail;
import DoAnJava.LinhKienDienTu.entity.compositeKey.BillDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetailKey> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bill_detail (product_id, bill_id, amount) " +
            "VALUES (?1, ?2, ?3)", nativeQuery = true)
    void addProductToBill(Long productId, Long billId, int amount);

}
