package DoAnJava.LinhKienDienTu.validator;

import DoAnJava.LinhKienDienTu.repository.UserRepository;
import DoAnJava.LinhKienDienTu.validator.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Autowired
    private UserRepository userReponsitory;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userReponsitory == null) {
            return true;
        }
        return userReponsitory.findByUsername(username) == null;
    }
}
