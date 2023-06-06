/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.moviemanager.utilities;

import hr.algebra.dal.Repository;
import hr.algebra.models.Actor;
import hr.algebra.models.Director;
import hr.algebra.models.Movie;
import hr.algebra.models.MovieActor;
import hr.algebra.models.MovieDirector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nix
 */
public class MovieUtility {

    private MovieUtility() {
    }

    public static void createMovie(
            Movie movie,
            Repository<Movie> movieRepo,
            Repository<MovieDirector> movieDirectorRepo,
            Repository<MovieActor> movieActorRepo,
            List<Director> directorsFromDb,
            List<Actor> actorsFromDb) throws Exception {
        int movieId = movieRepo.createSingle(movie);
        int directorId;
        int actorId;
        List<MovieDirector> movieDirectors = new ArrayList<>();
        List<MovieActor> movieActors = new ArrayList<>();

        for (Director director : movie.getDirectors()) {
            for (Director directorFromDb : directorsFromDb) {
                if (director.getFirstName().equals(directorFromDb.getFirstName()) && director.getLastName().equals(directorFromDb.getLastName())) {
                    directorId = directorFromDb.getId();
                    movieDirectors.add(new MovieDirector(movieId, directorId));
                    break;
                }
            }
        }

        for (Actor actor : movie.getActors()) {
            for (Actor actorFromDb : actorsFromDb) {
                if (actor.getFirstName().equals(actorFromDb.getFirstName()) && actor.getLastName().equals(actorFromDb.getLastName())) {
                    actorId = actorFromDb.getId();
                    movieActors.add(new MovieActor(movieId, actorId));
                    break;
                }
            }
        }

        movieDirectorRepo.createMultiple(movieDirectors);
        movieActorRepo.createMultiple(movieActors);
    }

}
