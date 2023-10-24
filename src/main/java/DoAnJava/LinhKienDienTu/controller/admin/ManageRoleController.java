package DoAnJava.LinhKienDienTu.controller.admin;

import DoAnJava.LinhKienDienTu.entity.Role;
import DoAnJava.LinhKienDienTu.services.RoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/roles")
@AllArgsConstructor
public class ManageRoleController {

    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String getAllRole(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/role/list-role";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-role")
    public String addRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin/role/add-role";
    }

    @PostMapping("/add-role")
    public String addRole(@Valid @ModelAttribute("role") Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
            }
            return "admin/role/add-role";
        }
        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete-role/{roleId}")
    public String deleteRole(@PathVariable("roleId") UUID roleId) {
        roleService.removeRole(roleId);
        return "redirect:/admin/roles";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit-role/{roleId}")
    public String editRoleForm(@PathVariable("roleId") UUID roleId, Model model) {
        Role role = roleService.getRoleById(roleId);
        model.addAttribute("role", role);
        return "admin/role/edit-role";
    }

    @PostMapping("/edit-role/{roleId}")
    public String editRole(@Valid @ModelAttribute("role") Role role,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error", error.getDefaultMessage());
            }
            return "admin/role/edit-role";
        }
        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }
}
