/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.models.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author Nix
 */
public class MovieRepository implements Repository<Movie> {

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DURATION = "Duration";
    private static final String YEAR_OF_RELEASE = "YearOfRelease";
    private static final String GENRE = "Genre";
    private static final String POSTER = "Poster";
    private static final String TYPE_OF_MOVIE = "TypeOfMovie";
    private static final String LINK = "Link";
    private static final String RESERVATION = "Reservation";
    private static final String DATE_OF_DISPLAY = "DateOfDisplay";
    private static final String SORT = "Sort";
    private static final String TRAILER = "Trailer";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    @Override
    public int createSingle(Movie entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE);) {
            stmt.setString(TITLE, entity.getTitle());
            stmt.setString(PUBLISHED_DATE, entity.getPublishedDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(DESCRIPTION, entity.getDescription());
            stmt.setString(ORIGINAL_TITLE, entity.getOriginalTitle());
            stmt.setInt(DURATION, entity.getDuration());
            stmt.setInt(YEAR_OF_RELEASE, entity.getYearOfRelease());
            stmt.setString(GENRE, entity.getGenre());
            stmt.setString(POSTER, entity.getPoster());
            stmt.setString(TYPE_OF_MOVIE, entity.getTypeOfMovie());
            stmt.setString(LINK, entity.getLink());
            stmt.setString(RESERVATION, entity.getReservation());
            stmt.setString(DATE_OF_DISPLAY, entity.getDateOfDisplay().format(Movie.DATE_FORMATTER));
            stmt.setInt(SORT, entity.getSort());
            stmt.setString(TRAILER, entity.getTrailer());

            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public void createMultiple(List<Movie> entities) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_MOVIE);) {
            for (Movie entity : entities) {
                stmt.setString(TITLE, entity.getTitle());
                stmt.setString(PUBLISHED_DATE, entity.getPublishedDate().format(Movie.DATE_TIME_FORMATTER));
                stmt.setString(DESCRIPTION, entity.getDescription());
                stmt.setString(ORIGINAL_TITLE, entity.getOriginalTitle());
                stmt.setInt(DURATION, entity.getDuration());
                stmt.setInt(YEAR_OF_RELEASE, entity.getYearOfRelease());
                stmt.setString(GENRE, entity.getGenre());
                stmt.setString(POSTER, entity.getPoster());
                stmt.setString(TYPE_OF_MOVIE, entity.getTypeOfMovie());
                stmt.setString(LINK, entity.getLink());
                stmt.setString(RESERVATION, entity.getReservation());
                stmt.setString(DATE_OF_DISPLAY, entity.getDateOfDisplay().format(Movie.DATE_FORMATTER));
                stmt.setInt(SORT, entity.getSort());
                stmt.setString(TRAILER, entity.getTrailer());

                stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Movie entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(UPDATE_MOVIE);) {
            stmt.setString(TITLE, entity.getTitle());
            stmt.setString(PUBLISHED_DATE, entity.getPublishedDate().format(Movie.DATE_TIME_FORMATTER));
            stmt.setString(DESCRIPTION, entity.getDescription());
            stmt.setString(ORIGINAL_TITLE, entity.getOriginalTitle());
            stmt.setInt(DURATION, entity.getDuration());
            stmt.setInt(YEAR_OF_RELEASE, entity.getYearOfRelease());
            stmt.setString(GENRE, entity.getGenre());
            stmt.setString(POSTER, entity.getPoster());
            stmt.setString(TYPE_OF_MOVIE, entity.getTypeOfMovie());
            stmt.setString(LINK, entity.getLink());
            stmt.setString(RESERVATION, entity.getReservation());
            stmt.setString(DATE_OF_DISPLAY, entity.getDateOfDisplay().format(Movie.DATE_FORMATTER));
            stmt.setInt(SORT, entity.getSort());
            stmt.setString(TRAILER, entity.getTrailer());

            stmt.setInt(ID_MOVIE, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(DELETE_MOVIE);) {
            stmt.setInt(ID_MOVIE, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectSingle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE);) {
            stmt.setInt(ID_MOVIE, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_TIME_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getInt(DURATION),
                            rs.getInt(YEAR_OF_RELEASE),
                            rs.getString(GENRE),
                            rs.getString(POSTER),
                            rs.getString(TYPE_OF_MOVIE),
                            rs.getString(LINK),
                            rs.getString(RESERVATION),
                            LocalDate.parse(rs.getString(DATE_OF_DISPLAY), Movie.DATE_FORMATTER),
                            rs.getInt(SORT),
                            rs.getString(TRAILER)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Movie> selectAll() throws Exception {
        List<Movie> movies = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_TIME_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getInt(DURATION),
                        rs.getInt(YEAR_OF_RELEASE),
                        rs.getString(GENRE),
                        rs.getString(POSTER),
                        rs.getString(TYPE_OF_MOVIE),
                        rs.getString(LINK),
                        rs.getString(RESERVATION),
                        LocalDate.parse(rs.getString(DATE_OF_DISPLAY), Movie.DATE_FORMATTER),
                        rs.getInt(SORT),
                        rs.getString(TRAILER)
                ));
            }
        }

        return movies;
    }
}
