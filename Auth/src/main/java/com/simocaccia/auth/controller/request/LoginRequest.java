package com.simocaccia.auth.controller.request;

import com.simocaccia.auth.util.Role;

import java.util.Collection;

public record LoginRequest(String username, String password, Collection<Role> authorities) {
}
