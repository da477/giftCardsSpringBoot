package org.da477.springsecurity.service;

import org.da477.springsecurity.model.User;

public interface UserService {

    void save(User user);

    User findByEmail(String email);
}
