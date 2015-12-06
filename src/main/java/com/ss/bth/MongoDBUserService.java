package com.ss.bth;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    public UserDTO register(UserDTO user) {
        String activationCode = generateActivationCode(user.getPrimaryEmail());
		
        ActivationEmailDTO activationEmail = new ActivationEmailDTO();
        activationEmail.setActivationCode(activationCode);
        activationEmail.setEmail(user.getPrimaryEmail());

        User persistedUser = User.getBuilder()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setPrimaryEmail(user.getPrimaryEmail())
                .setSecondaryEmail(user.getSecondaryEmail())
                .setPassword(user.getPassword())
                .setAddress(user.getAddress())
                .setZipCode(user.getZipCode())
                .setCity(user.getCity())
                .setTelephone(user.getTelephone())
                .setMobile(user.getMobile())
                .setActivationCode(activationCode)
                .setActivated(false)
                .setBlocked(false)
                .build();

		persistedUser = repository.insert(persistedUser);
        emailQueueConfiguration.rabbitTemplate().convertAndSend(EmailQueueConfiguration.ACTIVATION_EMAIL_QUEUE, activationEmail);
        return convertToDTO(persistedUser);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public List<UserDTO> findAll() {
        Stream<User> userEntries = repository.findAllBy();
        return convertToDTOs(userEntries.collect(Collectors.toList()));
    }

    @Override
    public UserDTO findById(String id) {
        return convertToDTO(repository.findOneById(id).get());
    }

    @Override
    public UserDTO update(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO activateAccount(UserActivateDTO userActivateDTO) {
        User user = repository.findByPrimaryEmail(userActivateDTO.getPrimaryEmail());
        if (user.getPassword().equals(userActivateDTO.getPassword()) && user.getActivationCode().equals(userActivateDTO.getActivationCode())) {
            user.setActivated(true);
            repository.save(user);
            return convertToDTO(repository.findOneById(user.getId()).get());
        }
        return null;
    }

    @Override
    public UserDTO authenticate(UserAuthenticateDTO userAuthenticateDTO) {
        User user = repository.findByPrimaryEmail(userAuthenticateDTO.getPrimaryEmail());
        if (user.getPassword().equals(userAuthenticateDTO.getPassword()) && user.isActivated() && !user.isBlocked()) {
            return convertToDTO(repository.findOneById(user.getId()).get());
        }
        return null;
    }

    private List<UserDTO> convertToDTOs(List<User> models) {
        return models.stream().map(this::convertToDTO).collect(toList());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
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
