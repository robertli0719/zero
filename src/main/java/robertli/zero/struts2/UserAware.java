/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
public interface UserAware {

    public void setUser(User user);
}
