/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestAttribute;
import robertli.zero.core.ClientAccessTokenManager;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserAuthPasswordDto;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.service.AuthService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/me")
public class MeController {

    @Resource
    private AuthService authService;

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private ClientAccessTokenManager clientAccessTokenManager;

    @RequestMapping(method = RequestMethod.GET)
    public UserProfileDto getMe(@RequestAttribute(required = false) String accessToken) {
        return authService.getUserProfile(accessToken);
    }

    @RequestMapping(path = "auth", method = RequestMethod.PUT)
    public void putAuth(@Valid @RequestBody UserAuthDto userAuthDto, HttpServletResponse response) {
        String accessTokenO = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String accessToken = clientAccessTokenManager.countAccessToken(accessTokenO);

        authService.putAuth(accessToken, userAuthDto);
        clientAccessTokenManager.putAccessTokenO(response, accessTokenO);
    }

    @RequestMapping(path = "auth", method = RequestMethod.DELETE)
    public void deleteAuth(@RequestAttribute(required = false) String accessToken, HttpServletResponse response) {
        if (accessToken == null) {
            return;
        }
        authService.deleteAuth(accessToken);
        clientAccessTokenManager.deleteAccessToken(response);
    }

    @RequestMapping(path = "auth/password", method = RequestMethod.PUT)
    public void putAuthPassword(@RequestAttribute(required = false) String accessToken, @Valid @RequestBody UserAuthPasswordDto userAuthPasswordDto) {
        authService.resetPassword(accessToken, userAuthPasswordDto);
    }

}
