package com.challenge.restful.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created as a model object for data access
 */
@XmlRootElement
@Entity
public class Gif implements Serializable{
    @XmlElement
    private int idGif;
    @XmlElement
    private String gif_url;
    @XmlElement
    private String category;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idgif")
    public int getIdGif() {
        return idGif;
    }

    public void setIdGif(int idgif) {
        this.idGif = idgif;
    }

    @Basic
    @Column(name = "gif_url")
    public String getGif_url() {
        return gif_url;
    }

    public void setGif_url(String gif) {
        this.gif_url = gif;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gif)) return false;
        Gif gif = (Gif) o;
        return Objects.equals(getIdGif(), gif.getIdGif()) &&
                Objects.equals(getGif_url(), gif.getGif_url()) &&
                Objects.equals(getCategory(), gif.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdGif(), getGif_url(), getCategory());
    }

    @Override
    public String toString() {
        return "Gif{" +
                "idGif=" + idGif +
                ", gif_url='" + gif_url + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
