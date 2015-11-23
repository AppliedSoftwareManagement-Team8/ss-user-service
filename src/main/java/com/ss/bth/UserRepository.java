package com.ss.bth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Samuil on 21-11-2015
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Query(fields="{ 'password' : 0, 'activationCode' : 0 }")
    Stream<User> findAllBy();

    @Query(fields="{ 'password' : 0, 'activationCode' : 0 }")
    Optional<User> findOneById(String id);

    User findByPrimaryEmail(String primaryEmail);
}
