/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 *
 * @version 1.0.0 2017-06-01
 * @author Robert Li
 */
public interface SmsSender {

    public void send(String phoneNumber, String message);
}
