/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hr.algebra.models.enums;

import hr.algebra.models.Actor;
import hr.algebra.models.AppUser;
import hr.algebra.models.Director;
import hr.algebra.models.Movie;

/**
 *
 * @author Nix
 */
public enum RepoType {
    USER(AppUser.class.toString()),
    DIRECTOR(Director.class.toString()),
    ACTOR(Actor.class.toString()),
    MOVIE(Movie.class.toString());

    private final String className;

    private RepoType(String className) {
        this.className = className;
    }

    public String getClassOfRepository() {
        return className;
    }
}
