/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

/**
 * WebConfiguration is a singleton object to storage configuration information
 * in presentation layer. This object can be use over business layer.
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface WebConfiguration {

    public String getDomain();

    public String getGoogleSigninClientId();

}
