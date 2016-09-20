/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

/**
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface SecurityService {

    /**
     * For security reason, there should be nobody can know the password except
     * users themselves, so we use three salts with MD5 or similar algorithm to
     * protect password.<br>
     * orginealPassword is the real password which only the user know, and
     * password is what we exactly save in our database.<br>
     *
     * password = processFun(orginealPassword, salt, serverSalt)<br>
     *
     * In my deign, only the server administrator can get the serverSalt, and
     * the administrator will never be allow to get the database access
     * permission.
     *
     * @param orginealPassword
     * @param salt each user should own a different salt for MD5 or similar
     * algorithm
     * @return password
     */
    public String uglifyPassoword(String orginealPassword, String salt);

    /**
     * create a password salt for uglifyPassoword
     *
     * @return salt
     */
    public String createPasswordSalt();
}
