import com.example.RentCar.Models.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends  BaseModel implements UserDetails {

    @NotNull
    private String userName;

    @NotNull
    private String userEmail;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming your User class has a List<Role> roles property
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Assuming no account expiration logic for now
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Assuming no account locking logic for now
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Assuming no credential expiration logic for now
    }

    @Override
    public boolean isEnabled() {
        return true; // Assuming the user is enabled
    }
}
