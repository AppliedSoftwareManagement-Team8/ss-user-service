package com.ss.bth;

import java.util.List;

/**
 * Created by Samuil on 21-11-2015
 */
public interface UserService {

    UserDTO register(UserDTO user);

    void delete(String id);

    List<UserDTO> findAll();

    UserDTO findById(String id);

    UserDTO update(UserDTO user);

    UserDTO activateAccount(UserActivateDTO userActivateDTO);

    UserDTO authenticate(UserAuthenticateDTO userAuthenticateDTO);
}
