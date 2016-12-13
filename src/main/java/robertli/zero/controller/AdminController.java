/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

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
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AppService appService;

    @RequestMapping(path = "init", method = RequestMethod.PUT)
    public void init() {
        Logger logger = Logger.getLogger("AdminController");
        logger.log(Level.INFO, "init database");
        appService.init();
    }

}
