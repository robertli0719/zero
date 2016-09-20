/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.struts2;

import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
public interface UserAware {

    public void setUser(User user);
}
