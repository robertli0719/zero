/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.DemoDto;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("demo")
public class DemoController extends GenericRestController {

    @RequestMapping(value = "fun", method = POST)
    public Map<String, Object> fun(@Valid @RequestBody DemoDto demoDto) {
        System.out.println("fun");
        System.out.println("demoDto.id:" + demoDto.getId());
        System.out.println("demoDto.name:" + demoDto.getName());
        System.out.println("demoDto.dateTime:" + demoDto.getDateTime());

        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
