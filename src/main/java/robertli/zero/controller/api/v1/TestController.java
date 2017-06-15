/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import robertli.zero.controller.RestException;
import robertli.zero.dto.DemoDto;
import robertli.zero.dto.PagingModal;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @RequestMapping(path = "demos/{id}", method = RequestMethod.GET)
    public DemoDto getDemo(@PathVariable int id, HttpServletRequest request) {
        System.out.println("\n\ngetDemo:");

        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String header = request.getHeader(name);
            System.out.println(name + " : " + header);
        }

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
    public List<DemoDto> getDemos(@RequestAttribute PagingModal pagingModal) {
        pagingModal.placeHeaders(100);

        List<DemoDto> demoDtoList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final DemoDto demo = new DemoDto();
            demo.setId(i);
            demo.setDateTime(new Date());
            demo.setDecimal(new BigDecimal((i - 10) + "." + i));
            demo.setName("testName:" + i);
            demoDtoList.add(demo);
            if (pagingModal.getLimit() != 0 && i + 1 >= pagingModal.getLimit()) {
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
        System.out.println("name:" + demoDto.getName());
        System.out.println("datetime:" + demoDto.getDateTime());
        System.out.println("subItem:" + demoDto.getSubItem());
    }

    @RequestMapping(path = "error", method = RequestMethod.GET)
    public UserProfileDto getError() {
        String status = "ERROR_TEST";
        String message = "test for throw new RestException";
        String detail = "test info detail";
        throw new RestException(status, message, detail, HttpStatus.FORBIDDEN);
    }
}
