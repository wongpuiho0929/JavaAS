/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Exception;

/**
 *
 * @author User
 */
public class SameUsernameException extends Exception {

    public SameUsernameException(String username) {
        super("Username already registered:" + username);
    }

}
