package com.challenge.restful.security;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created for json mapping purposes if needed
 */
@XmlRootElement
public class AuthResponseBody {

    @XmlElement
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
