/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package temp;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;

/**
 *
 * @author Robert Li
 */
@ApplicationScoped
public class MyTest {

    public static void fun() {
        UserEntity user = new UserEntity();
        user.setAddress("7651 gilbert rd");
        user.setIconPath("/img/user3.png");
        user.setId(-123);
        user.setPassword("sfidfewifhowefhowefewf");
        user.setUsername("tom");
        System.out.println(user);
        runSomething(user);

    }

    public static void runSomething(@Valid UserEntity userEntity) {
        System.out.println("runSomething:");
        System.out.println(userEntity);
    }

    public static void main(String args[]) {
        fun();
    }
}
