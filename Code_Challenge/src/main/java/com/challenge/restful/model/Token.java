package com.challenge.restful.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created as a model object for data access
 */
@XmlRootElement
@Entity
public class Token implements Serializable {
    @XmlElement
    private String username;
    @XmlElement
    private String token;
    @XmlElement
    private Timestamp creationDate;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "creationDate")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(username, token1.username) &&
                Objects.equals(token, token1.token) &&
                Objects.equals(creationDate, token1.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token, creationDate);
    }
}
