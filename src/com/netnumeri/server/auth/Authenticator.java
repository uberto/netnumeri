package com.netnumeri.server.auth;

import com.netnumeri.shared.finance.finpojo.User;

public interface Authenticator {
    public User authenticate(String username, String passwordHash);
}
