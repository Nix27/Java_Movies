/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.models;

import hr.algebra.models.Actor;
import hr.algebra.models.Director;
import hr.algebra.models.Movie;
import java.util.List;

/**
 *
 * @author Nix
 */
public class MovieVM {
    private Movie movie;
    private List<Director> directors;
    private List<Actor> actors;

    public MovieVM() {
    }

    public MovieVM(Movie movie, List<Director> directors, List<Actor> actors) {
        this.movie = movie;
        this.directors = directors;
        this.actors = actors;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
