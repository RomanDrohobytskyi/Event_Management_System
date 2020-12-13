package application.entities.user;

import application.roles.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity (name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 1, max = 24)
    private String username;
    @NotNull
    @Size(min = 1, max = 34)
    private String firstName;
    @NotNull
    @Size(min = 1, max = 42)
    private String lastName;
    @NotNull
    private String password;
    private String activationCode;
    private boolean active = false;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<Role> roles;

    public boolean isAdmin(){
        return getRoles().contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public String getNames(){
        return firstName + " " +lastName;
    }

    @Override
    public String toString() {
        return "User{ " +
                "id = " + id +
                ", email = '" + email + '\'' +
                ", username = '" + username + '\'' +
                ", names = '" + getNames() + '\'' +
                '}';
    }


}
