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
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;

@Component("userAuthDao")
public class UserAuthDaoImpl extends GenericHibernateDao<UserAuth, String> implements UserAuthDao {

    @Override
    public UserAuth saveUserAuth(String authId, String label, String type, User user) {
        UserAuth userAuth = new UserAuth();
        userAuth.setAuthId(authId);
        userAuth.setLabel(label);
        userAuth.setType(type);
        userAuth.setUser(user);
        save(userAuth);
        return userAuth;
    }

}
