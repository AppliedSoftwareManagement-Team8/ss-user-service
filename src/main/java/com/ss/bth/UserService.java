package com.ss.bth;

import java.util.List;

/**
 * Created by Samuil on 21-11-2015
 */
public interface UserService {

    UserDAO register(UserDAO user);

    void delete(String id);

    List<UserDAO> findAll();

    UserDAO findById(String id);

    UserDAO update(UserUpdateDAO user);

    UserDAO activateAccount(UserActivateDAO userActivateDAO);

    UserDAO authenticate(UserAuthenticateDAO userAuthenticateDAO);

    UserDAO convertToDAO(User user);

    UserUpdateDAO convertToUpdateDAO(UserDAO userDAO);

    UserRepository getRepository();
}
