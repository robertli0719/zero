/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

import java.io.IOException;
import java.util.Map;

/**
 * This service is for using Google sign-in for web site.<br />
 * reference:<br/>
 * https://developers.google.com/identity/sign-in/web/backend-auth
 * <br/>
 *
 *
 * @version 1.0 2016-09-20
 * @author Robert Li
 */
public interface GoogleAuthService {

    public Map<String, String> getInfo(String token) throws IOException;
}
