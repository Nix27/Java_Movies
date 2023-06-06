/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
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
public class MovieDirectorRepository implements Repository<MovieDirector> {

    private static final String ID_MOVIE_DIRECTOR = "IDMovieDirector";
    private static final String MOVIE_ID = "MovieId";
    private static final String DIRECTOR_ID = "DirectorId";

    private static final String CREATE_MOVIE_DIRECTOR = "{ CALL createMovieDirector (?,?,?) }";
    private static final String UPDATE_MOVIE_DIRECTOR = "{ CALL updateMovieDirector (?,?,?) }";
    private static final String DELETE_MOVIE_DIRECTOR = "{ CALL deleteMovieDirector (?) }";
    private static final String SELECT_MOVIE_DIRECTOR = "{ CALL selectMovieDirector (?) }";
    private static final String SELECT_MOVIE_DIRECTORS = "{ CALL selectMovieDirectors }";

    @Override
    public int createSingle(MovieDirector entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE_DIRECTOR);) {
            stmt.setInt(MOVIE_ID, entity.getMovieId());
            stmt.setInt(DIRECTOR_ID, entity.getDirectorId());

            stmt.registerOutParameter(ID_MOVIE_DIRECTOR, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_MOVIE_DIRECTOR);
        }
    }

    @Override
    public List<MovieDirector> createMultiple(List<MovieDirector> entities) throws Exception {
        List<MovieDirector> movieDirectors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE_DIRECTOR);) {
            for (MovieDirector entity : entities) {
                stmt.setInt(MOVIE_ID, entity.getMovieId());
                stmt.setInt(DIRECTOR_ID, entity.getDirectorId());

                stmt.registerOutParameter(ID_MOVIE_DIRECTOR, Types.INTEGER);

                stmt.executeUpdate();
                
                int id = stmt.getInt(ID_MOVIE_DIRECTOR);
                
                movieDirectors.add(new MovieDirector(
                        id,
                        entity.getMovieId(),
                        entity.getDirectorId()
                ));
            }
        }
        
        return movieDirectors;
    }

    @Override
    public void update(int id, MovieDirector entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(UPDATE_MOVIE_DIRECTOR);) {
            stmt.setInt(MOVIE_ID, entity.getMovieId());
            stmt.setInt(DIRECTOR_ID, entity.getDirectorId());

            stmt.setInt(ID_MOVIE_DIRECTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(DELETE_MOVIE_DIRECTOR);) {
            stmt.setInt(ID_MOVIE_DIRECTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<MovieDirector> selectSingle(int movieId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_DIRECTOR);) {
            stmt.setInt(MOVIE_ID, movieId);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new MovieDirector(
                            rs.getInt(ID_MOVIE_DIRECTOR),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(DIRECTOR_ID)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<MovieDirector> selectAll() throws Exception {
        List<MovieDirector> movieDirectors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_DIRECTORS); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                movieDirectors.add(new MovieDirector(
                        rs.getInt(ID_MOVIE_DIRECTOR),
                        rs.getInt(MOVIE_ID),
                        rs.getInt(DIRECTOR_ID)
                ));
            }
        }

        return movieDirectors;
    }

    @Override
    public List<MovieDirector> selectMultiple(int movieId) throws Exception {
        List<MovieDirector> movieDirectors = new ArrayList<>();
        
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_DIRECTOR);) {
            stmt.setInt(MOVIE_ID, movieId);
            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    movieDirectors.add(new MovieDirector(
                            rs.getInt(ID_MOVIE_DIRECTOR),
                            rs.getInt(MOVIE_ID),
                            rs.getInt(DIRECTOR_ID)
                    ));
                }
            }
        }
        
        return movieDirectors;
    }
}
