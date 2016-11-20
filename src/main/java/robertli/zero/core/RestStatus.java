/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * Define all possible response states for our Rest API.<br>
 * we don't need to throw 400, 405 and 5xx by hand.
 *
 * @version 1.0 2016-11-19
 * @author Robert Li
 */
public interface RestStatus {

    final int OK = 200;
    final int CREATED = 201;
    final int ACCEPTED = 202;
    final int NOT_MODIFIED = 304;
    final int FORBIDDEN = 403;
    final int NOT_FOUND = 404;
    final int CONFLICT = 409;
}
