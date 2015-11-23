package com.ss.bth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Samuil on 21-11-2015
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<UserDTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    UserDTO findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    String delete(@PathVariable("id") String id) {
        service.delete(id);
        return "{ \"deleted\" : true }";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO create(@RequestBody @Valid UserDTO userEntry) {
        return service.register(userEntry);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody @Valid UserDTO userEntry) {
        return service.update(userEntry);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    UserDTO activateAccount(@RequestBody @Valid UserActivateDTO userActivateDTO) {
        UserDTO userDTO = service.activateAccount(userActivateDTO);
        if (userDTO == null) {
            throw new IllegalArgumentException("The parameters are not valid!");
        }
        return userDTO;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    UserDTO authenticate(@RequestBody @Valid UserAuthenticateDTO userAuthenticateDTO) {
        UserDTO userDTO = service.authenticate(userAuthenticateDTO);
        if (userDTO == null) {
            throw new IllegalArgumentException("The parameters are not valid!");
        }
        return userDTO;
    }
}
