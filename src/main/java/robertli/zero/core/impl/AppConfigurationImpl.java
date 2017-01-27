/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.util.TimeZone;
import javax.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.ConfigurationChecker;

public class AppConfigurationImpl implements AppConfiguration, InitializingBean {

    @Resource
    private ConfigurationChecker configurationChecker;

    private String md5Salt;
    private String timeZone;
    private String userIdSeedP;
    private String userIdSeedQ;
    private String initAdminName;
    private String initAdminPassword;

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
        this.timeZone = timeZone;
    }

    @Override
    public String getUserIdSeedP() {
        return userIdSeedP;
    }

    public void setUserIdSeedP(String userIdSeedP) {
        this.userIdSeedP = userIdSeedP;
    }

    @Override
    public String getUserIdSeedQ() {
        return userIdSeedQ;
    }

    public void setUserIdSeedQ(String userIdSeedQ) {
        this.userIdSeedQ = userIdSeedQ;
    }

    @Override
    public String getInitAdminName() {
        return initAdminName;
    }

    public void setInitAdminName(String initAdminName) {
        this.initAdminName = initAdminName;
    }

    @Override
    public String getInitAdminPassword() {
        return initAdminPassword;
    }

    public void setInitAdminPassword(String initAdminPassword) {
        this.initAdminPassword = initAdminPassword;
    }

    @Override
    public void afterPropertiesSet() {
        configurationChecker.check();
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}
