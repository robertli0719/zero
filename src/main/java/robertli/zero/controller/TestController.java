/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import robertli.zero.dto.DemoDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping(path = "demos/{id}", method = RequestMethod.GET)
    public DemoDto getDemo(@PathVariable int id) {
        DemoDto demo = new DemoDto();
        demo.setId(id);
        demo.setDateTime(new Date());
        demo.setName("testName:" + id);
        return demo;
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

    @RequestMapping(path = "error", method = RequestMethod.GET)
    public UserProfileDto getError() {
        String status = "ERROR_TEST";
        String message = "test for throw new RestException";
        String detail = "test info detail";
        throw new RestException(status, message, detail, HttpStatus.FORBIDDEN);
    }
}
