package DoAnJava.LinhKienDienTu.controller.admin;

import DoAnJava.LinhKienDienTu.entity.Role;
import DoAnJava.LinhKienDienTu.entity.User;
import DoAnJava.LinhKienDienTu.services.RoleService;
import DoAnJava.LinhKienDienTu.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@AllArgsConstructor
public class ManageUserController {

    private UserService userService;
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String getAllUser(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/list-user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/assign-role/{id}")
    public String addRoleToUserForm(@PathVariable("id") UUID id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        String[] rolesOfUser = roleService.getRolesOfUser(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("roleOfUser", rolesOfUser);
        return "admin/role/assign-role";
    }

    @PostMapping("/assign-role")
    public String addRoleToUser(@RequestParam UUID userId,
                                @RequestParam UUID roleId, RedirectAttributes redirectAttributes) {
        String[] roles = roleService.getRolesOfUser(userId);
        String roleName = roleService.getRoleById(roleId).getRoleName();

        if (Arrays.asList(roles).contains(roleName)) {
            redirectAttributes.addFlashAttribute("exists", "Quyền đã tồn tại cho người dùng này");
            return "redirect:/admin/assign-role/" + userId;
        } else {
            roleService.addRoleToUser(userId, roleId);
            redirectAttributes.addFlashAttribute("success", "Đã thêm quyền cho người dùng này");
        }
        return "redirect:/admin/assign-role/" + userId;
    }

    @PostMapping("/remove-role-from-user")
    public String removeRoleFromUser(@RequestParam("userId") UUID userId,
                                     @RequestParam("roleId") UUID roleId, RedirectAttributes redirectAttributes) {
        String[] roles = roleService.getRolesOfUser(userId);
        String roleName = roleService.getRoleById(roleId).getRoleName();

        if (Arrays.asList(roles).contains(roleName)) {
            roleService.removeRoleFromUser(userId, roleId);
            redirectAttributes.addFlashAttribute("success", "Đã xóa quyền cho người dùng này");
        } else {
            redirectAttributes.addFlashAttribute("notExist", "Người dùng không có quyền này");
        }

        return "redirect:/admin/assign-role/" + userId;
    }
}
