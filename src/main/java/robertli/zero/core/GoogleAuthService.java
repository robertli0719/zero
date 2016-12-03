/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.io.IOException;
import java.util.Map;

/**
 * This service is for using Google sign-in for web site.<br>
 * reference:<br>
 * https://developers.google.com/identity/sign-in/web/backend-auth
 * <br>
 *
 *
 * @version 1.0 2016-09-20
 * @author Robert Li
 */
public interface GoogleAuthService {

    public Map<String, String> getInfo(String token) throws IOException;
}
