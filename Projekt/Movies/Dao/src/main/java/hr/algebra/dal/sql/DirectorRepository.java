/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.models.Director;
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
public class DirectorRepository implements Repository<Director> {

    private static final String ID_DIRECTOR = "IDDirector";
    private static final String FIRSTNAME = "FirstName";
    private static final String LASTNAME = "LastName";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";

    @Override
    public int createSingle(Director entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_DIRECTOR);) {
            stmt.setString(FIRSTNAME, entity.getFirstName());
            stmt.setString(LASTNAME, entity.getLastName());

            stmt.registerOutParameter(ID_DIRECTOR, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_DIRECTOR);
        }
    }

    @Override
    public void createMultiple(List<Director> entities) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_DIRECTOR);) {
            for (Director entity : entities) {
                stmt.setString(FIRSTNAME, entity.getFirstName());
                stmt.setString(LASTNAME, entity.getLastName());

                stmt.registerOutParameter(ID_DIRECTOR, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Director entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(UPDATE_DIRECTOR);) {
            stmt.setString(FIRSTNAME, entity.getFirstName());
            stmt.setString(LASTNAME, entity.getLastName());

            stmt.setInt(ID_DIRECTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(DELETE_DIRECTOR);) {
            stmt.setInt(ID_DIRECTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> selectSingle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_DIRECTOR);) {
            stmt.setInt(ID_DIRECTOR, id);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(FIRSTNAME),
                            rs.getString(LASTNAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Director> selectAll() throws Exception {
        List<Director> directors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_DIRECTORS); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(FIRSTNAME),
                        rs.getString(LASTNAME)
                ));
            }
        }

        return directors;
    }
}
