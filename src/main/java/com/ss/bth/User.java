package com.ss.bth;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Samuil on 21-11-2015
 * A User Model class
 */
@Document(collection = "user")
@TypeAlias("user")
@CompoundIndexes({
        @CompoundIndex(name = "email_code", def = "{'primaryEmail' : 1, 'activationCode': 1}", unique = true)
})
public final class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private String secondaryEmail;
    private String password;
    private String address;
    private String city;
    private int zipCode;
    private int telephone;
    private int mobile;
    private String activationCode;
    private boolean isActivated;
    private boolean isBlocked;

    public User() {}

    private User(UserBuilder userBuilder) {
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.primaryEmail = userBuilder.primaryEmail;
        this.secondaryEmail = userBuilder.secondaryEmail;
        this.password = userBuilder.password;
        this.address = userBuilder.address;
        this.city = userBuilder.city;
        this.zipCode = userBuilder.zipCode;
        this.telephone = userBuilder.telephone;
        this.mobile = userBuilder.mobile;
        this.activationCode = userBuilder.activationCode;
        this.isActivated = userBuilder.isActivated;
        this.isBlocked = userBuilder.isBlocked;
    }

    public void update() {
        //TODO Update
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getTelephone() {
        return telephone;
    }

    public int getMobile() {
        return mobile;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    static class UserBuilder {

        private String firstName;
        private String lastName;
        private String primaryEmail;
        private String secondaryEmail;
        private String password;
        private String address;
        private String city;
        private int zipCode;
        private int telephone;
        private int mobile;
        private String activationCode;
        private boolean isActivated;
        private boolean isBlocked;

        private UserBuilder() {
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setPrimaryEmail(String primaryEmail) {
            this.primaryEmail = primaryEmail;
            return this;
        }

        public UserBuilder setSecondaryEmail(String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder setZipCode(int zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public UserBuilder setTelephone(int telephone) {
            this.telephone = telephone;
            return this;
        }

        public UserBuilder setMobile(int mobile) {
            this.mobile = mobile;
            return this;
        }

        public UserBuilder setActivated(boolean activated) {
            this.isActivated = activated;
            return this;
        }

        public UserBuilder setBlocked(boolean blocked) {
            this.isBlocked = blocked;
            return this;
        }

        public UserBuilder setActivationCode(String activationCode) {
            this.activationCode = activationCode;
            return this;
        }

        User build() {
            User build = new User(this);
            return build;
        }
    }
}
