/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.models;

import hr.algebra.adapters.PublishedDateAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Nix
 */
@XmlType(propOrder = {"id", "title", "publishedDate", "description", "originalTitle", "duration", "yearOfRelease", "genre", "poster", "link", "reservation",
"trailer", "directors", "actors"})
public class Movie {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private int id;
    private String title;
    private LocalDateTime publishedDate;
    private String description;
    private String originalTitle;
    private int duration;
    private int yearOfRelease;
    private String genre;
    private String poster;
    private String link;
    private String reservation;
    private String trailer;
    private List<Director> directors;
    private List<Actor> actors;

    public Movie() {
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, int duration, int yearOfRelease, String genre, String poster, String link, String reservation, String trailer) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.poster = poster;
        this.link = link;
        this.reservation = reservation;
        this.trailer = trailer;
        directors = new ArrayList<>();
        actors = new ArrayList<>();
    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String originalTitle, int duration, int yearOfRelease, String genre, String poster, String link, String reservation, String trailer) {
        this(title, publishedDate, description, originalTitle, duration, yearOfRelease, genre, poster, link, reservation, trailer);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlJavaTypeAdapter(PublishedDateAdapter.class)
    @XmlElement(name = "published_date")
    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "original_title")
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @XmlElement(name = "year_of_release")
    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getReservation() {
        return reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @XmlElementWrapper
    @XmlElement(name = "director")
    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    @XmlElementWrapper
    @XmlElement(name = "actor")
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        return Objects.equals(this.title, other.title);
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}
