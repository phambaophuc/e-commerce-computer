package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.Role;
import DoAnJava.LinhKienDienTu.repository.RoleRepository;
import DoAnJava.LinhKienDienTu.repository.UserRepository;
import DoAnJava.LinhKienDienTu.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(UUID id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void removeRole(UUID roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public void addRoleToUser(UUID userId, UUID roleId) {
        userRepository.addRoleToUser(userId, roleId);
    }

    @Override
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        userRepository.removeRoleFromUser(userId, roleId);
    }

    @Override
    public String[] getRolesOfUser(UUID id) {
        return userRepository.getRolesOfUser(id);
    }
}
