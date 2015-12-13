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
    private double rating;
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private String secondaryEmail;
    private String password;
    private String address;
    private String city;
    private int zipCode;
    private String telephone;
    private String mobile;
    private String activationCode;
    private boolean isActivated;
    private boolean isBlocked;

    public User() {
    }

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

    public void update(double rating, String firstName, String lastName, String secondaryEmail, String password,
                       String address, String city, int zipCode, String telephone, String mobile, boolean isBlocked) {
        StringUtil strUtil = new StringUtil();
        if (rating >= 1 && rating <= 5)
            this.rating = rating;
        if (!strUtil.isBlank(firstName) && !strUtil.isEmpty(firstName) && strUtil.isLengthBetween(firstName, 2, 30))
            this.firstName = firstName;
        if (!strUtil.isBlank(lastName) && !strUtil.isEmpty(lastName) && strUtil.isLengthBetween(lastName, 2, 30))
            this.lastName = lastName;
        if (!strUtil.isBlank(secondaryEmail) && !strUtil.isEmpty(secondaryEmail))
            this.secondaryEmail = secondaryEmail;
        if (!strUtil.isBlank(password) && !strUtil.isEmpty(password))
            this.password = password;
        if (!strUtil.isBlank(address) && !strUtil.isEmpty(address) && strUtil.isLengthBetween(address, 5, 50))
            this.address = address;
        if (!strUtil.isBlank(city) && !strUtil.isEmpty(city) && strUtil.isLengthBetween(city, 2, 50))
            this.city = city;
        if (zipCode >= 10000 && zipCode <= 99999)
            this.zipCode = zipCode;
        if (!strUtil.isBlank(telephone) && !strUtil.isEmpty(telephone) && strUtil.isLengthBetween(telephone, 9, 10))
            this.telephone = telephone;
        if (!strUtil.isBlank(mobile) && !strUtil.isEmpty(mobile) && strUtil.isLengthBetween(mobile, 9, 10))
            this.mobile = mobile;
        if (this.isBlocked != isBlocked)
            this.isBlocked = isBlocked;
    }

    public String getId() {
        return id;
    }

    public double getRating() {
        return rating;
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

    public String getTelephone() {
        return telephone;
    }

    public String getMobile() {
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

        private double rating;
        private String firstName;
        private String lastName;
        private String primaryEmail;
        private String secondaryEmail;
        private String password;
        private String address;
        private String city;
        private int zipCode;
        private String telephone;
        private String mobile;
        private String activationCode;
        private boolean isActivated;
        private boolean isBlocked;

        private UserBuilder() {
        }

        public UserBuilder setRating(double rating) {
            this.rating = rating;
            return this;
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

        public UserBuilder setTelephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public UserBuilder setMobile(String mobile) {
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

    public class StringUtil {

        public boolean isEmpty(String str) {
            if ((str != null) && (str.trim().length() > 0))
                return false;
            else
                return true;
        }

        public boolean isBlank(String str) {
            int strLen;
            if (str == null || (strLen = str.length()) == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if ((!Character.isWhitespace(str.charAt(i)))) {
                    return false;
                }
            }
            return true;
        }

        public boolean isLengthBetween(String str, int min, int max) {
            int strLen;
            if (str == null || (strLen = str.length()) == 0) {
                return false;
            }
            if (strLen >= min && strLen <= max)
                return true;
            else
                return false;
        }
    }
}
