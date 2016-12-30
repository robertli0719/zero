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
public class UserManagementServiceTest {

    private final UserManagementService userManagementService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    public UserManagementServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userManagementService = (UserManagementService) context.getBean("userManagementService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    public void test1() throws JsonProcessingException {
        List<UserPlatformDto> userPlatformDtoList= userManagementService.getUserPlatformList();
        ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(userPlatformDtoList);
        System.out.println(json);
    }

    public void test() throws JsonProcessingException {
        test1();
    }

    public static void main(String args[]) throws JsonProcessingException {
        UserManagementServiceTest test = new UserManagementServiceTest();
        test.test();
    }
}
