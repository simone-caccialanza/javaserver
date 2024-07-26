package com.simocaccia.auth.repository;

import com.simocaccia.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsernameOrEmailIgnoreCase(@Nullable String username, @Nullable String email);
}
