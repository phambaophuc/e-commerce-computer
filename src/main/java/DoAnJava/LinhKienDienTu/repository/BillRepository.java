package DoAnJava.LinhKienDienTu.repository;

import DoAnJava.LinhKienDienTu.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT MONTH(b.createdAt) AS month, SUM(b.totalPrice) AS total " +
            "FROM Bill b " +
            "WHERE YEAR(b.createdAt) = :year " +
            "GROUP BY MONTH(b.createdAt)")
    List<Object[]> getMonthlyRevenue(@Param("year") int year);

    @Query("SELECT DAY(b.createdAt) AS day, SUM(b.totalPrice) AS total " +
            "FROM Bill b " +
            "WHERE MONTH(b.createdAt) = :month AND YEAR(b.createdAt) = :year " +
            "GROUP BY DAY(b.createdAt)")
    List<Object[]> getDailyRevenue(@Param("month") int month, @Param("year") int year);
}
