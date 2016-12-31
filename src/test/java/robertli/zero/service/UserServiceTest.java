/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.UserPlatformDto;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private final UserService userService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    public UserServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = (UserService) context.getBean("userService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    public void test1() throws JsonProcessingException {
        List<UserPlatformDto> userPlatformDtoList= userService.getUserPlatformList();
        ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(userPlatformDtoList);
        System.out.println(json);
    }

    public void test() throws JsonProcessingException {
        test1();
    }

    public static void main(String args[]) throws JsonProcessingException {
        UserServiceTest test = new UserServiceTest();
        test.test();
    }
}
