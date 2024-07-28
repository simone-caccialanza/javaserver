package com.simocaccia.auth.entity;

import com.simocaccia.auth.util.GrantedAuthorityCollectionConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user_auth")
public class UserAuth implements UserDetails, CredentialsContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "authorities", nullable = false)
    @Convert(converter = GrantedAuthorityCollectionConverter.class)
    private Set<GrantedAuthority> authorities;
    @Column(name = "account_not_expired", nullable = false)
    private boolean accountNonExpired;
    @Column(name = "account_not_locked", nullable = false)
    private boolean accountNonLocked;
    @Column(name = "credentials_not_expired", nullable = false)
    private boolean credentialsNonExpired;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    private void onCreate() {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = false;
        createdAt = LocalDateTime.now();
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Builder style setters
     */
    public UserAuth username(String username) {
        this.username = username;
        return this;
    }

    public UserAuth password(String password) {
        this.password = password;
        return this;
    }

    public UserAuth email(String email) {
        this.email = email;
        return this;
    }

    public UserAuth authorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }
}
