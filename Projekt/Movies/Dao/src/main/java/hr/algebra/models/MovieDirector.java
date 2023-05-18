/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.models;

/**
 *
 * @author Nix
 */
public class MovieDirector {
    private int id;
    private int MovieId;
    private int DirectorId;

    public MovieDirector(int MovieId, int DirectorId) {
        this.MovieId = MovieId;
        this.DirectorId = DirectorId;
    }

    public MovieDirector(int id, int MovieId, int DirectorId) {
        this(MovieId, DirectorId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return MovieId;
    }

    public int getDirectorId() {
        return DirectorId;
    }
}
