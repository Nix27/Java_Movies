/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.models.Actor;
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
public class ActorRepository implements Repository<Actor>{
    private static final String ID_ACTOR = "IDActor";
    private static final String FIRSTNAME = "FirstName";
    private static final String LASTNAME = "LastName";

    private static final String CREATE_ACTOR = "{ CALL createActor (?,?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";

    @Override
    public int createSingle(Actor entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_ACTOR);) {
            stmt.setString(FIRSTNAME, entity.getFirstName());
            stmt.setString(LASTNAME, entity.getLastName());

            stmt.registerOutParameter(ID_ACTOR, Types.INTEGER);

            stmt.executeUpdate();

            return stmt.getInt(ID_ACTOR);
        }
    }

    @Override
    public void createMultiple(List<Actor> entities) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_ACTOR);) {
            for (Actor entity : entities) {
                stmt.setString(FIRSTNAME, entity.getFirstName());
                stmt.setString(LASTNAME, entity.getLastName());

                stmt.registerOutParameter(ID_ACTOR, Types.INTEGER);

                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void update(int id, Actor entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(UPDATE_ACTOR);) {
            stmt.setString(FIRSTNAME, entity.getFirstName());
            stmt.setString(LASTNAME, entity.getLastName());

            stmt.setInt(ID_ACTOR, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(DELETE_ACTOR);) {
            stmt.setInt(ID_ACTOR, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Actor> selectSingle(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_ACTOR);) {
            stmt.setInt(ID_ACTOR, id);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(FIRSTNAME),
                            rs.getString(LASTNAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Actor> selectAll() throws Exception {
        List<Actor> actors = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_ACTORS); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(FIRSTNAME),
                        rs.getString(LASTNAME)
                ));
            }
        }

        return actors;
    }
}
