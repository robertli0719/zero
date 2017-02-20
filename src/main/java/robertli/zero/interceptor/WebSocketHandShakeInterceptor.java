/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.interceptor;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import robertli.zero.core.ClientAccessTokenManager;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.service.AuthService;

/**
 *
 * @author Robert Li
 */
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    @Resource
    private ClientAccessTokenManager clientAccessTokenManager;

    @Resource
    private AuthService authService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsh, Map<String, Object> map) throws Exception {
        String accessToken = clientAccessTokenManager.getAccessToken(request);
        if (accessToken == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        UserProfileDto userProfileDto = authService.getUserProfile(accessToken);
        if (userProfileDto == null || userProfileDto.getUid() == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }
        map.put("uid", userProfileDto.getUid());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsh, Exception excptn) {
    }

}
