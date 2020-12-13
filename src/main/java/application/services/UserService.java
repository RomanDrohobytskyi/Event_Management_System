package application.services;

import application.entities.user.User;
import application.enums.UserRegisterValidationState;
import application.repositories.UserRepository;
import application.roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByEmail(username);
    }

    public User findUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public boolean isUserExist(User user){
        return userRepository.findUserByEmail(user.getEmail()).isPresent();
    }

    public boolean isUserEmailEmpty(User user){
        return StringUtils.isEmpty(user.getEmail());
    }

    public boolean isPasswordsMatch(String password, String confirmedPassword){
        return password.equals(confirmedPassword);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void encodeUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public Map<String, Object> validateUser(User user, String passwordConfirm) {
        Map<String, Object> emailAndPasswordValidationResult = validateUserEmailAndPasswords(user, passwordConfirm);
        if (emailAndPasswordValidationResult.isEmpty()) {
            setNewUserData(user);
            return sendActivationCode(user);
        } else {
            return emailAndPasswordValidationResult;
        }
    }

    private Map<String, Object> sendActivationCode(User user){
        if (sendActivationCodeForUser(user)) {
            saveUser(user);
            return Map.of(UserRegisterValidationState.SUCCESS.toString(), UserRegisterValidationState.SUCCESS.state);
        } else {
            return Map.of(UserRegisterValidationState.CODE_SENDING_FAILED.toString(), UserRegisterValidationState.CODE_SENDING_FAILED.state);
        }
    }

    private void setNewUserData(User user){
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(generateVerificationToken());
        encodeUserPassword(user);
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public boolean sendActivationCodeForUser(User user){
        try{
            String message = String.format(
                    "Hello, %s!\n" + "Welcome to the Aim Taker!\n" +
                            "To activate your account, please, click on a link below.\n" +
                            "http://localhost:8080/activate/" + user.getActivationCode() + "\n" +
                            "Thank You " +  user.getFirstName() + " " + user.getLastName(),
                    user.getFirstName() + " " + user.getLastName()
            );
            mailSenderService.send(user.getEmail(), "Aim Taker. Activation code", message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private Map<String, Object> validateUserEmailAndPasswords(User user, String passwordConfirm){
        if (isUserEmailEmpty(user)) {
            return Map.of(UserRegisterValidationState.EMPTY_EMAIL.toString(), UserRegisterValidationState.EMPTY_EMAIL.state);
        } else if (isUserExist(user)) {
            return Map.of(UserRegisterValidationState.USER_EXIST.toString(), UserRegisterValidationState.USER_EXIST.state);
        } else if (!isPasswordsMatch(user.getPassword(), passwordConfirm)) {
            return Map.of(UserRegisterValidationState.PASSWORDS_NOT_MATCHING.toString(), UserRegisterValidationState.PASSWORDS_NOT_MATCHING.state);
        } else {
            return Collections.emptyMap();
        }
    }

    public void activateUserByActivationCode(String code) {
        User user = findByActivationCode(code).orElseThrow(IllegalArgumentException::new);
        user.setActivationCode(null);
        user.setActive(true);
        saveUser(user);
    }

    public Optional<User> findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }

}
