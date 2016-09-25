/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * AppConfiguration is a singleton object to storage configuration information
 * under business layer. For security consideration, don't use it in
 * presentation layer (ex. Action, Intercepter in Struts2)
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface AppConfiguration {

    public String getMd5Salt();

    public String getTimeZone();

    public String getInitAdminName();

    public String getInitAdminPassword();
}
