package DoAnJava.LinhKienDienTu.services.impl;

import DoAnJava.LinhKienDienTu.entity.User;
import DoAnJava.LinhKienDienTu.enums.AuthenticationType;
import DoAnJava.LinhKienDienTu.repository.RoleRepository;
import DoAnJava.LinhKienDienTu.repository.UserRepository;
import DoAnJava.LinhKienDienTu.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Không tìm thấy user với email là: " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomStringUtils.randomAlphanumeric(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        user.setAuthType(AuthenticationType.LOCAL);

        UUID userId = userRepository.getUserIdByUsername(user.getUsername());
        UUID roleId = roleRepository.getRoleIdByRoleName("USER");
        if (roleId != null && userId != null) {
            userRepository.addRoleToUser(userId, roleId);
        }

        sendVerificationEmail(user, siteURL);
        userRepository.save(user);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }

    @Override
    public void sendForgotPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromEmail, "Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void updateAuthenticationType(String username, String email, String fullname, String oauth2ClientName) {
        User existUser = userRepository.findByUsername(username);
        AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setFullname(fullname);
            newUser.setEnabled(true);

            userRepository.save(newUser);
            userRepository.updateAuthenticationType(username, authType);

            UUID userId = userRepository.getUserIdByUsername(username);
            UUID roleId = roleRepository.getRoleIdByRoleName("USER");
            if (roleId != null && userId != null) {
                userRepository.addRoleToUser(userId, roleId);
            }
        } else {
            userRepository.updateAuthenticationType(username, authType);
        }
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromEmail;
        String senderName = "Phạm Bảo Phúc";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFullname());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
