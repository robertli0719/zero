/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

/**
 * This service can do some common operation for the database.
 *
 * @version 1.0 2016-12-09
 * @author Robert Li
 */
public interface AppService {

    /**
     * insert the initialized data to database for the first time to use this
     * system.<br>
     *
     * If this function has been run before, it does nothing.
     */
    public void init();
}
