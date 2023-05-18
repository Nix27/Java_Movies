/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.models.enums.RepoType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nix
 */
public class RepositoryFactory {

    private static Map<RepoType, Repository> repositories = new HashMap<>();
    
    private RepositoryFactory() {
    }
    
    public static Repository getRepository(RepoType repoType) throws Exception{
        Repository repository = repositories.get(repoType);
        
        if(repository == null){
            repository = (Repository) Class.forName(repoType.getClassOfRepository())
                    .getDeclaredConstructor()
                    .newInstance();
            repositories.put(repoType, repository);
        }
        
        return repository;
    }
}
