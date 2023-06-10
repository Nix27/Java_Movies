/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.services;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.models.Actor;
import hr.algebra.models.Director;
import hr.algebra.models.Movie;
import hr.algebra.models.MovieActor;
import hr.algebra.models.MovieDirector;
import hr.algebra.models.enums.RepoType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Nix
 */
public class MovieService {

    private Repository<Movie> movieRepo;
    private Repository<Director> directorRepo;
    private Repository<Actor> actorRepo;
    private Repository<MovieDirector> movieDirectorRepo;
    private Repository<MovieActor> movieActorRepo;

    public MovieService() {
        try {
            initRepo();
        } catch (Exception ex) {
            Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        movieRepo = RepositoryFactory.getRepository(RepoType.MOVIE);
        directorRepo = RepositoryFactory.getRepository(RepoType.DIRECTOR);
        actorRepo = RepositoryFactory.getRepository(RepoType.ACTOR);
        movieDirectorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_DIRECTOR);
        movieActorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_ACTOR);
    }

    public List<Movie> loadAllMovies() throws Exception {
        List<Movie> allMovies = movieRepo.selectAll();

        for (Movie movie : allMovies) {
            movie.setDirectors(getDirectorsOfMovie(movie));
            movie.setActors(getActorsOfMovie(movie));
        }

        return allMovies;
    }

    public List<Director> getDirectorsOfMovie(Movie movie) throws Exception {
        List<Director> directors = new ArrayList<>();

        int movieId = movie.getId();
        List<MovieDirector> movieDirectors = movieDirectorRepo.selectMultiple(movieId);
        List<Integer> directorIds = movieDirectors.stream()
                .map(d -> d.getDirectorId())
                .collect(Collectors.toList());

        directorIds.forEach(id -> {
            try {
                directors.add(directorRepo.selectSingle(id).get());
            } catch (Exception ex) {
                Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return directors;
    }

    public List<Actor> getActorsOfMovie(Movie movie) throws Exception {
        List<Actor> actors = new ArrayList<>();

        int movieId = movie.getId();
        List<MovieActor> movieActors = movieActorRepo.selectMultiple(movieId);
        List<Integer> actorIds = movieActors.stream()
                .map(a -> a.getActorId())
                .collect(Collectors.toList());

        actorIds.forEach(id -> {
            try {
                actors.add(actorRepo.selectSingle(id).get());
            } catch (Exception ex) {
                Logger.getLogger(MovieService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return actors;
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieRepo.selectAll();
    }

    public List<Director> getAllDirectors() throws Exception {
        return directorRepo.selectAll();
    }

    public List<Actor> getAllActors() throws Exception {
        return actorRepo.selectAll();
    }

    public Optional<Movie> getMovie(int movieid) throws Exception {
        return movieRepo.selectSingle(movieid);
    }

    public void createMovie(Movie movie, Set<Director> directors, Set<Actor> actors) throws Exception {
        int movieId = movieRepo.createSingle(movie);

        if (!directors.isEmpty()) {
            addMovieDirectors(movieId, directors);
        }

        if (!actors.isEmpty()) {
            addMovieActors(movieId, actors);
        }
    }

    public void addMovieDirectors(int movieId, Set<Director> directors) throws Exception {
        List<MovieDirector> movieDirectors = new ArrayList<>();

        for (Director director : directors) {
            movieDirectors.add(new MovieDirector(movieId, director.getId()));
        }

        movieDirectorRepo.createMultiple(movieDirectors);
    }

    public void addMovieActors(int movieId, Set<Actor> actors) throws Exception {
        List<MovieActor> movieActors = new ArrayList<>();

        for (Actor actor : actors) {
            movieActors.add(new MovieActor(movieId, actor.getId()));
        }

        movieActorRepo.createMultiple(movieActors);
    }

    public void updateMovie(Movie movie, Set<Director> directors, Set<Actor> actors) throws Exception {
        movieRepo.update(movie.getId(), movie);

        updateMovieDirectorRelations(movie, directors);
        updateMovieActorRelations(movie, actors);
    }

    private void updateMovieDirectorRelations(Movie movie, Set<Director> directors) throws Exception {
        List<MovieDirector> movieDirectors = movieDirectorRepo.selectMultiple(movie.getId());

        List<Integer> directorIdsFromDb = movieDirectors
                .stream()
                .map(md -> md.getDirectorId())
                .collect(Collectors.toList());

        List<Integer> directorIdsFromList = directors
                .stream()
                .map(d -> d.getId())
                .collect(Collectors.toList());

        deleteOldMovieDirectorRelations(movieDirectors, directorIdsFromDb, directorIdsFromList);
        createNewMovieDirectorRelations(movie.getId(), movieDirectors, directorIdsFromDb, directorIdsFromList);
    }

    private void deleteOldMovieDirectorRelations(List<MovieDirector> movieDirectors, List<Integer> directorIdsFromDb, List<Integer> directorIdsFromList) throws Exception {
        List<Integer> directorIdsFromDbCopy = new ArrayList<>(directorIdsFromDb);
        directorIdsFromDbCopy.removeAll(directorIdsFromList);

        if (!directorIdsFromDbCopy.isEmpty()) {
            for (MovieDirector movieDirector : movieDirectors) {
                if (directorIdsFromDbCopy.contains(movieDirector.getDirectorId())) {
                    movieDirectorRepo.delete(movieDirector.getId());
                }
            }
        }
    }

    private void createNewMovieDirectorRelations(int movieId, List<MovieDirector> movieDirectors, List<Integer> directorIdsFromDb, List<Integer> directorIdsFromList) throws Exception {
        List<Integer> directorsIdsFromListCopy = new ArrayList<>(directorIdsFromList);
        directorsIdsFromListCopy.removeAll(directorIdsFromDb);

        if (!directorsIdsFromListCopy.isEmpty()) {
            for (Integer directorId : directorsIdsFromListCopy) {
                movieDirectorRepo.createSingle(new MovieDirector(movieId, directorId));
            }
        }
    }

    private void updateMovieActorRelations(Movie movie, Set<Actor> actors) throws Exception {
        List<MovieActor> movieActors = movieActorRepo.selectMultiple(movie.getId());

        List<Integer> actorIdsFromDb = movieActors
                .stream()
                .map(ma -> ma.getActorId())
                .collect(Collectors.toList());

        List<Integer> actorIdsFromList = actors
                .stream()
                .map(a -> a.getId())
                .collect(Collectors.toList());

        deleteOldMovieActorRelations(movieActors, actorIdsFromDb, actorIdsFromList);
        createNewMovieActorRelations(movie.getId(), movieActors, actorIdsFromDb, actorIdsFromList);
    }

    private void deleteOldMovieActorRelations(List<MovieActor> movieActors, List<Integer> actorIdsFromDb, List<Integer> actorIdsFromList) throws Exception {
        List<Integer> actorIdsFromDbCopy = new ArrayList<>(actorIdsFromDb);
        actorIdsFromDbCopy.removeAll(actorIdsFromList);

        if (!actorIdsFromDbCopy.isEmpty()) {
            for (MovieActor movieActor : movieActors) {
                if (actorIdsFromDbCopy.contains(movieActor.getActorId())) {
                    movieActorRepo.delete(movieActor.getId());
                }
            }
        }
    }

    private void createNewMovieActorRelations(int movieId, List<MovieActor> movieActors, List<Integer> actorIdsFromDb, List<Integer> actorIdsFromList) throws Exception {
        List<Integer> actorsIdsFromListCopy = new ArrayList<>(actorIdsFromList);
        actorsIdsFromListCopy.removeAll(actorIdsFromDb);

        if (!actorsIdsFromListCopy.isEmpty()) {
            for (Integer actorId : actorsIdsFromListCopy) {
                movieActorRepo.createSingle(new MovieActor(movieId, actorId));
            }
        }
    }

    public void deleteMovie(int movieId) throws Exception {
        List<MovieDirector> movieDirectors = movieDirectorRepo.selectMultiple(movieId);
        deleteMovieDirectors(movieDirectors);

        List<MovieActor> movieActors = movieActorRepo.selectMultiple(movieId);
        deleteMovieActors(movieActors);

        movieRepo.delete(movieId);
    }

    private void deleteMovieDirectors(List<MovieDirector> movieDirectors) throws Exception {
        for (MovieDirector movieDirector : movieDirectors) {
            movieDirectorRepo.delete(movieDirector.getId());
        }
    }

    private void deleteMovieActors(List<MovieActor> movieActors) throws Exception {
        for (MovieActor movieActor : movieActors) {
            movieActorRepo.delete(movieActor.getId());
        }
    }

    public void createMoviesOnParse(Set<Movie> movies) throws Exception {
        Set<Director> allDirectorsForDb = getADirectorsFromMovies(movies);
        Set<Actor> allActorsForDb = getActorsFromMovies(movies);

        List<Director> directorsFromDb = directorRepo.createMultiple(new ArrayList<>(allDirectorsForDb));
        List<Actor> actorsFromDb = actorRepo.createMultiple(new ArrayList<>(allActorsForDb));

        for (Movie movie : movies) {
            int movieId = movieRepo.createSingle(movie);
            createMovieDirectors(movieId, movie, directorsFromDb);
            createMovieActors(movieId, movie, actorsFromDb);
        }
    }

    private Set<Director> getADirectorsFromMovies(Set<Movie> movies) {
        Set<Director> directors = new HashSet<>();

        movies
                .forEach(m -> m.getDirectors()
                .forEach(directors::add));

        return directors;
    }

    private Set<Actor> getActorsFromMovies(Set<Movie> movies) {
        Set<Actor> actors = new HashSet<>();

        movies
                .forEach(m -> m.getActors()
                .forEach(actors::add));

        return actors;
    }

    private void createMovieDirectors(int movieId, Movie movie, List<Director> directorsFromDb) throws Exception {
        int directorId;
        List<MovieDirector> movieDirectors = new ArrayList<>();

        for (Director director : movie.getDirectors()) {
            for (Director directorFromDb : directorsFromDb) {
                if (director.getFirstName().equals(directorFromDb.getFirstName()) && director.getLastName().equals(directorFromDb.getLastName())) {
                    directorId = directorFromDb.getId();
                    movieDirectors.add(new MovieDirector(movieId, directorId));
                    break;
                }
            }
        }

        movieDirectorRepo.createMultiple(movieDirectors);
    }

    private void createMovieActors(int movieId, Movie movie, List<Actor> actorsFromDb) throws Exception {
        int actorId;
        List<MovieActor> movieActors = new ArrayList<>();

        for (Actor actor : movie.getActors()) {
            for (Actor actorFromDb : actorsFromDb) {
                if (actor.getFirstName().equals(actorFromDb.getFirstName()) && actor.getLastName().equals(actorFromDb.getLastName())) {
                    actorId = actorFromDb.getId();
                    movieActors.add(new MovieActor(movieId, actorId));
                    break;
                }
            }
        }
        
        movieActorRepo.createMultiple(movieActors);
    }
    
    public void deleteAll() throws Exception{
        movieRepo.deleteAll();
    }
}
