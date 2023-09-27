package DoAnJava.LinhKienDienTu.validator;

import DoAnJava.LinhKienDienTu.repository.UserRepository;
import DoAnJava.LinhKienDienTu.validator.annotation.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Autowired
    private UserRepository userReponsitory;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userReponsitory == null) {
            return true;
        }
        return userReponsitory.findByEmail(email) == null;
    }
}
