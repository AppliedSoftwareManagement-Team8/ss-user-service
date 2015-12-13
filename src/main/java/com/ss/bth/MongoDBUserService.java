package com.ss.bth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.DatatypeConverter;
import static java.util.stream.Collectors.toList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Samuil on 21-11-2015
 */
@Service
public class MongoDBUserService implements UserService {

    private final UserRepository repository;

    @Autowired
    MongoDBUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    EmailQueueConfiguration emailQueueConfiguration;

    @Override
    public UserDAO register(UserDAO user) {
        String activationCode = generateActivationCode(user.getPrimaryEmail());
		
        ActivationEmailDAO activationEmail = new ActivationEmailDAO();
        activationEmail.setActivationCode(activationCode);
        activationEmail.setEmail(user.getPrimaryEmail());

        User persistedUser = User.getBuilder()
                .setRating(0)
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setPrimaryEmail(user.getPrimaryEmail())
                .setSecondaryEmail(user.getSecondaryEmail())
                .setPassword(user.getPassword())
                .setAddress(user.getAddress())
                .setZipCode(10000)
                .setCity(user.getCity())
                .setTelephone(user.getTelephone())
                .setMobile(user.getMobile())
                .setActivationCode(activationCode)
                .setActivated(false)
                .setBlocked(false)
                .build();

		persistedUser = getRepository().insert(persistedUser);
        emailQueueConfiguration.rabbitTemplate().convertAndSend(EmailQueueConfiguration.ACTIVATION_EMAIL_QUEUE, activationEmail);
        return convertToDAO(persistedUser);
    }

    @Override
    public void delete(String id) {
        getRepository().delete(id);
    }

    @Override
    public List<UserDAO> findAll() {
        Stream<User> userEntries = getRepository().findAllBy();
        return convertToDTOs(userEntries.collect(Collectors.toList()));
    }

    @Override
    public UserDAO findById(String id) {
        return convertToDAO(getRepository().findOneById(id).get());
    }

    @Override
    public UserDAO update(UserUpdateDAO user) {
        User updatedUser = getRepository().findOne(user.getId());
        updatedUser.update(
                user.getRating(),
                user.getFirstName(),
                user.getLastName(),
                user.getSecondaryEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getCity(),
                user.getZipCode(),
                user.getTelephone(),
                user.getMobile(),
                user.isBlocked()
        );
        getRepository().save(updatedUser);
        return convertToDAO(getRepository().findOneById(user.getId()).get());
    }

    @Override
    public UserDAO activateAccount(UserActivateDAO userActivateDAO) {
        User user = getRepository().findByPrimaryEmail(userActivateDAO.getPrimaryEmail());
        if (user.getActivationCode().equals(userActivateDAO.getActivationCode())) {
            user.setActivated(true);
            getRepository().save(user);
            return convertToDAO(getRepository().findOneById(user.getId()).get());
        }
        return null;
    }

    @Override
    public UserDAO authenticate(UserAuthenticateDAO userAuthenticateDAO) {
        User user = getRepository().findByPrimaryEmail(userAuthenticateDAO.getPrimaryEmail());
        if (user.getPassword().equals(userAuthenticateDAO.getPassword()) && user.isActivated() && !user.isBlocked()) {
            return convertToDAO(getRepository().findOneById(user.getId()).get());
        }
        return null;
    }

    @Override
    public UserDAO convertToDAO(User user) {
        UserDAO dto = new UserDAO();
        dto.setId(user.getId());
        dto.setRating(user.getRating());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPrimaryEmail(user.getPrimaryEmail());
        dto.setSecondaryEmail(user.getSecondaryEmail());
        dto.setPassword(user.getPassword());
        dto.setAddress(user.getAddress());
        dto.setZipCode(user.getZipCode());
        dto.setCity(user.getCity());
        dto.setTelephone(user.getTelephone());
        dto.setMobile(user.getMobile());
        dto.setActivationCode(user.getActivationCode());
        dto.setActivated(user.isActivated());
        dto.setBlocked(user.isBlocked());
        return dto;
    }

    @Override
    public UserUpdateDAO convertToUpdateDAO(UserDAO userDAO) {
        UserUpdateDAO dto = new UserUpdateDAO();
        dto.setId(userDAO.getId());
        dto.setRating(userDAO.getRating());
        dto.setFirstName(userDAO.getFirstName());
        dto.setLastName(userDAO.getLastName());
        dto.setSecondaryEmail(userDAO.getSecondaryEmail());
        dto.setPassword(userDAO.getPassword());
        dto.setAddress(userDAO.getAddress());
        dto.setZipCode(userDAO.getZipCode());
        dto.setCity(userDAO.getCity());
        dto.setTelephone(userDAO.getTelephone());
        dto.setMobile(userDAO.getMobile());
        dto.setBlocked(userDAO.isBlocked());
        return dto;
    }

    @Override
    public UserRepository getRepository() {
        return repository;
    }

    private List<UserDAO> convertToDTOs(List<User> models) {
        return models.stream().map(this::convertToDAO).collect(toList());
    }

    private String generateActivationCode(String primaryEmail) {
        String salt1 = "@41kdE3!%x";
        String salt2 = "KFj4:|42fs";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String code = salt1 + primaryEmail + salt2;
        byte[] digest = md.digest(code.getBytes());
        return DatatypeConverter.printHexBinary(digest);
    }
}
