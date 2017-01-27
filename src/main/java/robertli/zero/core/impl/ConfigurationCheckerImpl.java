/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.math.BigInteger;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.ConfigurationChecker;
import robertli.zero.core.WebConfiguration;

@Component("configurationChecker")
public class ConfigurationCheckerImpl implements ConfigurationChecker {

    @Resource
    private AppConfiguration appConfiguration;

    @Resource
    private WebConfiguration webConfiguration;

    private void checkUserIdSeed(Logger logger) {
        if (appConfiguration.getUserIdSeedP() == null) {
            throw new RuntimeException("userIdSeedP could not be null");
        } else if (appConfiguration.getUserIdSeedQ() == null) {
            throw new RuntimeException("userIdSeedQ could not be null");
        }

        int p = 0, q = 0;
        try {
            p = Integer.parseInt(appConfiguration.getUserIdSeedP());
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("userIdSeedP can't be parsed to Integer");
        }
        try {
            q = Integer.parseInt(appConfiguration.getUserIdSeedQ());
        } catch (NumberFormatException nfe) {
            throw new RuntimeException("userIdSeedQ can't be parsed to Integer");
        }
        final int LESS_UID_LENGTH_LIMIT = 10000000;
        if (p <= 0 || q <= 0) {
            throw new RuntimeException("userIdSeedP and userIdSeedQ can't be less than 0");
        } else if (p <= q) {
            throw new RuntimeException("userIdSeedP should be greater than userIdSeedQ");
        } else if (q <= LESS_UID_LENGTH_LIMIT) {
            throw new RuntimeException("userIdSeedQ should be greater than " + LESS_UID_LENGTH_LIMIT);
        } else if (BigInteger.valueOf(p).isProbablePrime(1) == false) {
            throw new RuntimeException("userIdSeedP must be Prime.");
        } else if (BigInteger.valueOf(q).isProbablePrime(1) == false) {
            throw new RuntimeException("userIdSeedQ must be Prime.");
        }
    }

    @Override
    public void check() {
        Logger logger = Logger.getLogger("ConfigurationChecker");

        if (appConfiguration.getMd5Salt() == null || appConfiguration.getMd5Salt().length() != 32) {
            throw new RuntimeException("md5Salt should be 32 chars");
        }

        if (appConfiguration.getInitAdminName() == null) {
            throw new RuntimeException("initAdminName could not be null");
        }

        if (appConfiguration.getInitAdminPassword() == null) {
            throw new RuntimeException("initAdminPassword could not be null");
        }
        checkUserIdSeed(logger);
    }

}
