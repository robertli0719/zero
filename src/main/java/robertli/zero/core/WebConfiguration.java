/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * WebConfiguration is a singleton object to storage configuration information
 * in presentation layer. This object can be use over business layer.
 *
 * @version 1.0.3 2017-04-07
 * @author Robert Li
 */
public interface WebConfiguration {

    public String getDomain();

    public String getImageActionUrl();

    public String getVideoActionUrl();

    public String getGoogleSigninClientId();

    public boolean isSslEnabled();

}
