/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.entity.User;

/**
 * An external user is the user who register by themselves
 *
 * @version 1.0 2016-12-08
 * @author Robert Li
 */
public interface ExternalUserService {

    public User getUserProfile(String token);

}
