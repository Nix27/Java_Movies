/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.models.AppUser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Nix
 */
public class UserRepository implements Repository<AppUser>{

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ROLE = "Role";
    
    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?) }";
    private static final String AUTHENTICATE_USER = "{ CALL authenticateUser (?,?) }";
    
    @Override
    public int createSingle(AppUser entity) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        
        try(Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(CREATE_USER);){
            stmt.setString(USERNAME, entity.getUsername());
            stmt.setString(PASSWORD, entity.getPassword());
            stmt.setString(ROLE, entity.getRole());
            
            stmt.registerOutParameter(ID_USER, Types.INTEGER);
            
            stmt.executeUpdate();
            
            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public void createMultiple(List<AppUser> entities) throws Exception {
    }

    @Override
    public void update(int id, AppUser entity) throws Exception {
    }

    @Override
    public void delete(int id) throws Exception {
    }

    @Override
    public Optional<AppUser> selectSingle(int id) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<AppUser> selectAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public Optional<String> authenticate(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(AUTHENTICATE_USER);) {
            stmt.setString(USERNAME, username);
            stmt.setString(PASSWORD, password);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(
                            rs.getString(ROLE)
                    );
                }
            }
        }

        return Optional.empty();
    }
}
