package org.da477.springsecurity.security;

/**
 * Service for Security.
 *
 * @author da
 * @version 1.0
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String email, String password);

}
