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
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<UserDAO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    UserDAO findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    String delete(@PathVariable("id") String id) {
        service.delete(id);
        return "{ \"deleted\" : true }";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    UserDAO create(@RequestBody @Valid UserDAO userEntry) {
        return service.register(userEntry);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    UserDAO update(@RequestBody @Valid UserUpdateDAO userEntry) {
        return service.update(userEntry);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    UserDAO activateAccount(@RequestBody @Valid UserActivateDAO userActivateDAO) {
        UserDAO userDAO = service.activateAccount(userActivateDAO);
        if (userDAO == null) {
            throw new IllegalArgumentException("The parameters are not valid!");
        }
        return userDAO;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    UserDAO authenticate(@RequestBody @Valid UserAuthenticateDAO userAuthenticateDAO) {
        UserDAO userDAO = service.authenticate(userAuthenticateDAO);
        if (userDAO == null) {
            throw new IllegalArgumentException("The parameters are not valid!");
        }
        return userDAO;
    }
}
