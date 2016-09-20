/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.SecurityService;
import robertli.zero.tool.MD5;

@Component("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private AppConfiguration appConfiguration;

    /**
     * For security reason, there should be nobody can know the password except
     * users themselves, so we use three salts with MD5 or similar algorithm to
     * protect password.<br>
     * orginealPassword is the real password which only the user know, and
     * password is what we exactly save in our database.<br>
     *
     * password = MD5( MD5( MD5(orginealPassword) + salt ) + serverSalt)<br>
     *
     * In my deign, only the server administrator can get the serverSalt, and
     * the administrator will never be allow to get the database access
     * permission.
     *
     * @param orginealPassword
     * @param salt each user should own a different salt for MD5 or similar
     * algorithm
     * @return password
     */
    @Override
    public String uglifyPassoword(String orginealPassword, String salt) {
        String password = MD5.count(orginealPassword);
        password = MD5.count(password + salt);
        String serverSalt = appConfiguration.getMd5Salt();
        password = MD5.count(password + serverSalt);
        return password;
    }

    @Override
    public String createPasswordSalt() {
        return randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
    }

}
