/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.models;

/**
 *
 * @author Nix
 */
public class MovieActor {
    private int id;
    private int MovieId;
    private int ActorId;

    public MovieActor(int MovieId, int ActorId) {
        this.MovieId = MovieId;
        this.ActorId = ActorId;
    }

    public MovieActor(int id, int MovieId, int ActorId) {
        this(MovieId, ActorId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return MovieId;
    }

    public int getActorId() {
        return ActorId;
    }
}
