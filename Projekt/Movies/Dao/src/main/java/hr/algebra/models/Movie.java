/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Nix
 */
public class Movie {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    
    private int id;
    private String title;
    private LocalDateTime publishedDate;
    private String description;
    private String originalTitle;
    private int duration;
    private int yearOfRelease;
    private String genre;
    private String poster;
    private String typeOfMovie;
    private String link;
    private String reservation;
    private LocalDate dateOfDisplay;
    private int sort;
    private String trailer;

    public Movie() {
    }

    public Movie(String title, LocalDateTime publishedDate, String description, String originalTitle, int duration, int yearOfRelease, String genre, String poster, String typeOfMovie, String link, String reservation, LocalDate dateOfDisplay, int sort, String trailer) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.poster = poster;
        this.typeOfMovie = typeOfMovie;
        this.link = link;
        this.reservation = reservation;
        this.dateOfDisplay = dateOfDisplay;
        this.sort = sort;
        this.trailer = trailer;
    }

    public Movie(int id, String title, LocalDateTime publishedDate, String description, String originalTitle, int duration, int yearOfRelease, String genre, String poster, String typeOfMovie, String link, String reservation, LocalDate dateOfDisplay, int sort, String trailer) {
        this(title, publishedDate, description, originalTitle, duration, yearOfRelease, genre, poster, typeOfMovie, link, reservation, dateOfDisplay, sort, trailer);
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

    public String getTypeOfMovie() {
        return typeOfMovie;
    }

    public void setTypeOfMovie(String typeOfMovie) {
        this.typeOfMovie = typeOfMovie;
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

    public LocalDate getDateOfDisplay() {
        return dateOfDisplay;
    }

    public void setDateOfDisplay(LocalDate dateOfDisplay) {
        this.dateOfDisplay = dateOfDisplay;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}
