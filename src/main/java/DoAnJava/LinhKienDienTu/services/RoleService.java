package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(UUID id);

    void saveRole(Role role);

    void removeRole(UUID roleId);

}
