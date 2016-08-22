/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core.impl;

import java.util.List;

/**
 * This class is designed for instantiating DatabaseServiceImpl
 *
 * @version 1.0 2016-08-21
 * @author Robert Li
 */
public class DatabaseConfiguration {

    private String host;
    private String port;
    private String name;
    private String username;
    private String password;
    private List<String> entityPackageList;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getEntityPackageList() {
        return entityPackageList;
    }

    public void setEntityPackageList(List<String> entityPackageList) {
        this.entityPackageList = entityPackageList;
    }

}
