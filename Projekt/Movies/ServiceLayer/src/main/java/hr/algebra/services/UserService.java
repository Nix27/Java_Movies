/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.services;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.dal.sql.DataSourceSingleton;
import hr.algebra.interfaces.Authenticable;
import hr.algebra.models.AppUser;
import hr.algebra.models.enums.RepoType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Nix
 */
public class UserService implements Authenticable{
    
    private Repository<AppUser> userRepo;

    public UserService() {
        try {
            initRepo();
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        userRepo = RepositoryFactory.getRepository(RepoType.USER);
    }
    
    public void createUser(AppUser newUser) throws Exception{
        userRepo.createSingle(newUser);
    }

    private final String USERNAME = "Username";
    private final String PASSWORD = "Password";
    private final String ROLE = "Role";
    private final String AUTHENTICATE_USER = "{ CALL authenticateUser (?,?) }";
    
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
