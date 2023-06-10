/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.services;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.models.Director;
import hr.algebra.models.MovieDirector;
import hr.algebra.models.enums.RepoType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nix
 */
public class DirectorService {

    private Repository<Director> directorRepo;
    private Repository<MovieDirector> movieDirectorRepo;

    public DirectorService() {
        try {
            initRepo();
        } catch (Exception ex) {
            Logger.getLogger(DirectorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        directorRepo = RepositoryFactory.getRepository(RepoType.DIRECTOR);
        movieDirectorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_DIRECTOR);
    }

    public List<Director> getAllDirectors() throws Exception {
        return directorRepo.selectAll();
    }

    public Optional<Director> getDirector(int directorId) throws Exception {
        return directorRepo.selectSingle(directorId);
    }

    public void createDirector(Director director) throws Exception {
        directorRepo.createSingle(director);
    }

    public void updateDirector(Director director) throws Exception {
        directorRepo.update(director.getId(), director);
    }

    public void deleteDirector(Director director) throws Exception {
        List<MovieDirector> movieDirectors = movieDirectorRepo.selectAll();
        deleteMovieDirectors(director, movieDirectors);

        directorRepo.delete(director.getId());
    }

    private void deleteMovieDirectors(Director director, List<MovieDirector> movieDirectors) throws Exception {
        for (MovieDirector movieDirector : movieDirectors) {
            if(movieDirector.getDirectorId() == director.getId()){
                movieDirectorRepo.delete(director.getId());
            }
        }
    }
}
