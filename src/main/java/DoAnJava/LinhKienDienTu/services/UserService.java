package DoAnJava.LinhKienDienTu.services;

import DoAnJava.LinhKienDienTu.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    User getUserByUsername(String username);

    User getUserById(UUID id);

    void addRoleToUser(UUID userId, UUID roleId);

    void removeRoleFromUser(UUID userId, UUID roleId);

    String[] getRolesOfUser(UUID id);

    void saveUser(User user);

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;

    boolean verify(String verificationCode);

    void sendForgotPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;

    void updateAuthenticationType(String username, String email, String fullname, String oauth2ClientName);
}
