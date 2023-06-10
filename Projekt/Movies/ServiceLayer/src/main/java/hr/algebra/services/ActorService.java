/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.services;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.models.Actor;
import hr.algebra.models.MovieActor;
import hr.algebra.models.enums.RepoType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nix
 */
public class ActorService {
    private Repository<Actor> actorRepo;
    private Repository<MovieActor> movieActorRepo;
    
    public ActorService() {
        try {
            initRepo();
        } catch (Exception ex) {
            Logger.getLogger(ActorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        actorRepo = RepositoryFactory.getRepository(RepoType.ACTOR);
        movieActorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_ACTOR);
    }
    
    public List<Actor> getAllActors() throws Exception {
        return actorRepo.selectAll();
    }
    
    public Optional<Actor> getActor(int actorId) throws Exception {
        return actorRepo.selectSingle(actorId);
    }
    
    public void createActor(Actor actor) throws Exception {
        actorRepo.createSingle(actor);
    }
    
    public void updateActor(Actor actor) throws Exception {
        actorRepo.update(actor.getId(), actor);
    }
    
    public void deleteActor(Actor actor) throws Exception {
        List<MovieActor> movieActors = movieActorRepo.selectAll();
        deleteMovieActors(actor, movieActors);

        actorRepo.delete(actor.getId());
    }

    private void deleteMovieActors(Actor actor, List<MovieActor> movieActors) throws Exception {
        for (MovieActor movieActor : movieActors) {
            if(movieActor.getActorId()== actor.getId()){
                movieActorRepo.delete(actor.getId());
            }
        }
    }
}
