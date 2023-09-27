package DoAnJava.LinhKienDienTu.validator;

import DoAnJava.LinhKienDienTu.repository.IUserRepository;
import DoAnJava.LinhKienDienTu.validator.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Autowired
    private IUserRepository userReponsitory;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userReponsitory == null) {
            return true;
        }
        return userReponsitory.findByUsername(username) == null;
    }
}
