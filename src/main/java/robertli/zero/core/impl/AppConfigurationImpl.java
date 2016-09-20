/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core.impl;

import robertli.zero.core.AppConfiguration;

public class AppConfigurationImpl implements AppConfiguration {

    private String md5Salt;

    @Override
    public String getMd5Salt() {
        return md5Salt;
    }

    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
    }

}
