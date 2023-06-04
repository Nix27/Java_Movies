/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.interfaces;

import java.util.Optional;

/**
 *
 * @author Nix
 */
@FunctionalInterface
public interface Authenticable {
    Optional<String> authenticate(String username, String password) throws Exception;
}
