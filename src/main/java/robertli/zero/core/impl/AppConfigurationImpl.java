/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core.impl;

import java.util.TimeZone;
import robertli.zero.core.AppConfiguration;

public class AppConfigurationImpl implements AppConfiguration {
    
    private String md5Salt;
    private String timeZone;
    
    @Override
    public String getMd5Salt() {
        return md5Salt;
    }
    
    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
    }
    
    @Override
    public String getTimeZone() {
        return timeZone;
    }
    
    public void setTimeZone(String timeZone) {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        this.timeZone = timeZone;
    }
    
}
