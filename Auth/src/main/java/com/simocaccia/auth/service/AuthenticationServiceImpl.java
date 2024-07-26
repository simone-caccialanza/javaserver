package com.simocaccia.auth.service;

import com.simocaccia.auth.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public AuthenticationServiceImpl(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AccountStatusException {

        return userAuthRepository.findByUsernameOrEmailIgnoreCase(authentication.getPrincipal().toString(), authentication.getPrincipal().toString())
                .map(user -> {
                    if (!user.isAccountNonExpired()) {
                        throw new AccountExpiredException("Account expired");
                    }
                    if (!user.isAccountNonLocked()) {
                        throw new LockedException("Account locked");
                    }
                    if (!user.isCredentialsNonExpired()) {
                        throw new CredentialsExpiredException("Credentials expired");
                    }
                    if (!user.isEnabled()) {
                        throw new DisabledException("Credentials expired");
                    }

                    if (authentication.getCredentials().toString().equals(user.getPassword())) {
                        return UsernamePasswordAuthenticationToken.authenticated(
                                authentication.getPrincipal(),
                                authentication.getCredentials(),
                                user.getAuthorities());
                    } else {
                        throw new BadCredentialsException("Wrong username or password");
                    }
                })
                .orElseThrow(() -> new BadCredentialsException("Wrong username or password"));
    }
}
