/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        if (salt.length() < 32) {
            throw new RuntimeException("the salt length is not enough");
        } else if (serverSalt.length() < 32) {
            throw new RuntimeException("the serverSalt length is not enough");
        }
        return password;
    }

    @Override
    public String createPasswordSalt() {
        return randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
    }

}
