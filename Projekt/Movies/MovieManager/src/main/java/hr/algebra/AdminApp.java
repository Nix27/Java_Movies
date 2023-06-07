/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hr.algebra;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.models.Actor;
import hr.algebra.models.Movie;
import hr.algebra.models.Director;
import hr.algebra.models.MovieActor;
import hr.algebra.models.MovieDirector;
import hr.algebra.models.enums.RepoType;
import hr.algebra.parsers.rss.MovieParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Nix
 */
public class AdminApp extends javax.swing.JFrame {

    private static final String DIR = "assets";

    private Repository<Movie> movieRepo;
    private Repository<Director> directorRepo;
    private Repository<Actor> actorRepo;
    private Repository<MovieDirector> movieDirectorRepo;
    private Repository<MovieActor> movieActorRepo;

    private DefaultListModel<Movie> moviesModel;

    /**
     * Creates new form AdminApp
     */
    public AdminApp() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lsMovies = new javax.swing.JList<>();
        btnUpload = new javax.swing.JButton();
        btnDeleteAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin - Update data");

        jScrollPane1.setViewportView(lsMovies);

        btnUpload.setText("Upload data");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        btnDeleteAll.setText("Delete all");
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteAll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        try {
            Set<Movie> movies = MovieParser.parse();

            Set<Director> allDirectorsForDb = getAllDirectors(movies);
            Set<Actor> allActorsForDb = getAllActors(movies);

            List<Director> directorsFromDb = directorRepo.createMultiple(new ArrayList<>(allDirectorsForDb));
            List<Actor> actorsFromDb = actorRepo.createMultiple(new ArrayList<>(allActorsForDb));

            for (Movie movie : movies) {
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

            loadModel();
            changeButtonsEnableProperty();
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(AdminApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdminApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        try {
            movieRepo.deleteAll();
            deleteDirectory(new File(DIR));
            loadModel();
            changeButtonsEnableProperty();
        } catch (Exception ex) {
            Logger.getLogger(AdminApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnUpload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Movie> lsMovies;
    // End of variables declaration//GEN-END:variables

    private Set<Director> getAllDirectors(Set<Movie> movies) {
        Set<Director> directors = new HashSet<>();

        movies
                .forEach(m -> m.getDirectors()
                .forEach(directors::add));

        return directors;
    }

    private Set<Actor> getAllActors(Set<Movie> movies) {
        Set<Actor> actors = new HashSet<>();

        movies
                .forEach(m -> m.getActors()
                .forEach(actors::add));

        return actors;
    }

    private void init() {
        try {
            initRepo();
            moviesModel = new DefaultListModel<>();
            loadModel();
            changeButtonsEnableProperty();
        } catch (Exception ex) {
            Logger.getLogger(AdminApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        movieRepo = RepositoryFactory.getRepository(RepoType.MOVIE);
        directorRepo = RepositoryFactory.getRepository(RepoType.DIRECTOR);
        actorRepo = RepositoryFactory.getRepository(RepoType.ACTOR);
        movieDirectorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_DIRECTOR);
        movieActorRepo = RepositoryFactory.getRepository(RepoType.MOVIE_ACTOR);
    }

    private void loadModel() throws Exception {
        moviesModel.clear();
        List<Movie> movies = movieRepo.selectAll();

        movies.forEach(moviesModel::addElement);
        lsMovies.setModel(moviesModel);
    }

    private void deleteDirectory(File file) {
        File[] content = file.listFiles();
        if (content != null) {
            for (File f : content) {
                deleteDirectory(f);
            }
        }
        file.delete();
    }

    private void changeButtonsEnableProperty() {
        if (!moviesModel.isEmpty()) {
            btnUpload.setEnabled(false);
            btnDeleteAll.setEnabled(true);
        } else {
            btnUpload.setEnabled(true);
            btnDeleteAll.setEnabled(false);
        }
    }
}
