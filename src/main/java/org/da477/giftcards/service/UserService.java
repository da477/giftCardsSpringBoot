package org.da477.giftcards.service;

import org.da477.giftcards.model.User;

public interface UserService {

    void save(User user);

    User findByEmail(String email);
}
