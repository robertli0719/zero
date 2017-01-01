/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.service.AppService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/app")
public class AppController {

    @Resource
    private AppService appService;

    @RequestMapping(path = "init", method = RequestMethod.PUT)
    public void init() {
        Logger logger = Logger.getLogger("AppController");
        logger.log(Level.INFO, "init database");
        appService.init();
    }

}
