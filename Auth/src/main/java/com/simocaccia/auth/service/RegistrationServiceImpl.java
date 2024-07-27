package com.simocaccia.auth.service;

import com.simocaccia.auth.entity.UserAuth;
import com.simocaccia.auth.repository.UserAuthRepository;
import com.simocaccia.auth.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public RegistrationServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public void registerUser(String username, String password, String email) {
        userAuthRepository.save(
                new UserAuth()
                        .username(username)
                        .password(password)
                        .email(email)
                        .authorities(Set.of(Role.ROLE_USER))
        );
    }
}
