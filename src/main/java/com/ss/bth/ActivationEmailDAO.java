package com.ss.bth;

/**
 * Created by Samuil on 30-11-2015
 */
public class ActivationEmailDAO {

    private String email;
    private String activationCode;

    public ActivationEmailDAO() {}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getEmail() {
        return email;
    }

    public String getActivationCode() {
        return activationCode;
    }
}
