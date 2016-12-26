/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import robertli.zero.controller.RestException;
import robertli.zero.dto.DemoDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @RequestMapping(path = "demos/{id}", method = RequestMethod.GET)
    public DemoDto getDemo(@PathVariable int id) {
        DemoDto demo = new DemoDto();
        demo.setId(id);
        demo.setDateTime(new Date());
        demo.setName("testName:" + id);
        return demo;
    }

    @RequestMapping(path = "demos/{id}", method = RequestMethod.PUT)
    public void putDemos(@PathVariable int id, @Valid @RequestBody DemoDto demoDto) {
        if (demoDto.getId() == null) {
            throw new RestException("PUT_ERROR", "no id", "should include id when put");
        }
        System.out.println("\nput demo:");
        System.out.println("name" + demoDto.getName());
        System.out.println("datetime:" + demoDto.getDateTime());
    }

    @RequestMapping(path = "demos/{id}", method = RequestMethod.DELETE)
    public void deleteDemos(@PathVariable int id) {
        System.out.println("delete demo:" + id);
    }

    @RequestMapping(path = "demos", method = RequestMethod.GET)
    public List<DemoDto> getDemos(Integer max) {
        List<DemoDto> demoDtoList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DemoDto demo = new DemoDto();
            demo.setId(i);
            demo.setDateTime(new Date());
            demo.setName("testName:" + i);
            demoDtoList.add(demo);
            if (max != null && i + 1 >= max) {
                break;
            }
        }
        return demoDtoList;
    }

    @RequestMapping(path = "demos", method = RequestMethod.POST)
    public void postDemos(@Valid @RequestBody DemoDto demoDto) {
        if (demoDto.getId() != null) {
            throw new RestException("POST_ERROR", "can't include id when post", "can't include id when post");
        }
        System.out.println("\npost demo:");
        System.out.println("name" + demoDto.getName());
        System.out.println("datetime:" + demoDto.getDateTime());
    }

    @RequestMapping(path = "error", method = RequestMethod.GET)
    public UserProfileDto getError() {
        String status = "ERROR_TEST";
        String message = "test for throw new RestException";
        String detail = "test info detail";
        throw new RestException(status, message, detail, HttpStatus.FORBIDDEN);
    }
}
