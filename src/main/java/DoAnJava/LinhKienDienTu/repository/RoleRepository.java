package DoAnJava.LinhKienDienTu.repository;

import DoAnJava.LinhKienDienTu.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByRoleName(String roleName);

    @Query("SELECT r.roleId FROM Role r WHERE r.roleName = ?1")
    UUID getRoleIdByRoleName(String roleName);
}
