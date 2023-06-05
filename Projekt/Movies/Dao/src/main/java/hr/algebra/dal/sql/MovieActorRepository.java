/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.models.MovieActor;
import hr.algebra.models.MovieDirector;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Nix
 */
public class MovieActorRepository implements Repository<MovieActor>{

    private static final String ID_MOVIE_ACTOR = "IDMovieActor";
    private static final String MOVIE_ID = "MovieId";
    private static final String ACTOR_ID = "ActorId";

    private static final String CREATE_MOVIE_ACTOR = "{ CALL createMovieActor (?,?,?) }";
    private static final String UPDATE_MOVIE_ACTOR = "{ CALL updateMovieActor (?,?,?) }";
    private static final String DELETE_MOVIE_ACTOR = "{ CALL deleteMovieActor (?) }";
    private static final String SELECT_MOVIE_ACTOR = "{ CALL selectMovieActor (?) }";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors }";
    
    @Override
    public int createSingle(MovieActor entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE_ACTOR);) {
            stmt.setInt(MOVIE_ID, entity.getMovieId());
            stmt.setInt(ACTOR_ID, entity.getActorId());

            stmt.registerOutParameter(ID_MOVIE_ACTOR, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_MOVIE_ACTOR);
        }
    }

    @Override
    public void createMultiple(List<MovieActor> entities) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE_ACTOR);) {
            for (MovieActor entity : entities) {
                stmt.setInt(MOVIE_ID, entity.getMovieId());
                stmt.setInt(ACTOR_ID, entity.getActorId());

                stmt.registerOutParameter(ID_MOVIE_ACTOR, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, MovieActor entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(UPDATE_MOVIE_ACTOR);) {
            stmt.setInt(MOVIE_ID, entity.getMovieId());
            stmt.setInt(ACTOR_ID, entity.getActorId());

            stmt.setInt(ID_MOVIE_ACTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(DELETE_MOVIE_ACTOR);) {
            stmt.setInt(ID_MOVIE_ACTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MovieActor> selectSingle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_ACTOR);) {
            stmt.setInt(ID_MOVIE_ACTOR, id);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new MovieActor(
                            rs.getInt(ID_MOVIE_ACTOR),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(ACTOR_ID)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<MovieActor> selectAll() throws Exception {
        List<MovieActor> movieActors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_ACTORS); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                movieActors.add(new MovieActor(
                        rs.getInt(ID_MOVIE_ACTOR),
                        rs.getInt(MOVIE_ID),
                        rs.getInt(ACTOR_ID)
                ));
            }
        }

        return movieActors;
    }

    @Override
    public List<MovieActor> selectMultiple(int movieId) throws Exception {
        List<MovieActor> movieActors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_ACTOR);) {
            stmt.setInt(MOVIE_ID, movieId);
            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    movieActors.add(new MovieActor(
                            rs.getInt(ID_MOVIE_ACTOR),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(ACTOR_ID)
                    ));
                }
            }
        }
        
        return movieActors;
    }
}
