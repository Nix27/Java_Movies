/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hr.algebra.models.enums;

import hr.algebra.dal.sql.ActorRepository;
import hr.algebra.dal.sql.DirectorRepository;
import hr.algebra.dal.sql.MovieRepository;
import hr.algebra.dal.sql.UserRepository;

/**
 *
 * @author Nix
 */
public enum RepoType {
    USER(UserRepository.class.toString()),
    DIRECTOR(DirectorRepository.class.toString()),
    ACTOR(ActorRepository.class.toString()),
    MOVIE(MovieRepository.class.toString());

    private final String className;

    private RepoType(String className) {
        this.className = className;
    }

    public String getClassOfRepository() {
        return className.substring(6);
    }
}
