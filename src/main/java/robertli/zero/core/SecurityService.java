/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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
     * @param orginealPassword the real password
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
