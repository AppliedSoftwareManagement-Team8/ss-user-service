package com.ss.bth;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Samuil on 23-11-2015
 */
public class UserActivateDAO {

    @NotEmpty
    @Email
    private String primaryEmail;

    @NotEmpty
    private String activationCode;

    UserActivateDAO() {}

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}
