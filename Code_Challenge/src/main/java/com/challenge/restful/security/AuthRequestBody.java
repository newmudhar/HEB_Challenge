package com.challenge.restful.security;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created for json mapping purposes if needed
 */
@XmlRootElement
public class AuthRequestBody {

    @XmlElement
    private String username;

    @XmlElement
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
