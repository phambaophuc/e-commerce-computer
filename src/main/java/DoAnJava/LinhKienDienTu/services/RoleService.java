package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleById(UUID id);

    void saveRole(Role role);

    void removeRole(UUID roleId);

    void addRoleToUser(UUID userId, UUID roleId);

    void removeRoleFromUser(UUID userId, UUID roleId);

    String[] getRolesOfUser(UUID id);
}
