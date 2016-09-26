/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

/**
 * The struts2 Actions which implements AdminRootPermission should be used only
 * by root user. The user without root permission should be redirect to the
 * login page.
 *
 * @see AdminPermissionInterceptor
 * @version 1.0 2016-09-25
 * @author Robert Li
 */
public interface AdminRootPermission {

}
