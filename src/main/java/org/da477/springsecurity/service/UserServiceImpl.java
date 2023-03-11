package org.da477.springsecurity.service;

import org.da477.springsecurity.model.Role;
import org.da477.springsecurity.model.Status;
import org.da477.springsecurity.model.User;
import org.da477.springsecurity.repository.RoleDao;
import org.da477.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static org.da477.springsecurity.security.SecurityConfig.PASSWORD_ENCODER;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleDao roleDao) {
        this.userRepository = userRepository;
        this.roleDao = roleDao;
    }

    @Override
    public void save(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));

        String[] sentences = user.getEmail().split("@");

        user.setName(sentences[0]);
        user.setSurname(sentences[0]);

        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getReferenceById(1L)); // add: 1,ROLE_USER
        user.setRoles(roles);

        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
