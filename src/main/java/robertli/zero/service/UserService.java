package robertli.zero.service;

import robertli.zero.entity.User;
import robertli.zero.entity.UserRegister;

/**
 *
 * @version 1.0 2016-09-18
 * @author Robert Li
 */
public interface UserService {

    /**
     * Get the online user for this session id.
     *
     * @param sessionId the web session id for a client device or browser
     * @return current online user for this session id. null for no login yet
     */
    public User getCurrentUser(String sessionId);

    public enum UserLoginResult {
        SUCCESS,
        AUTH_OR_PASSWORD_WRONG
    }

    /**
     *
     * @param sessionId
     * @param auth
     * @param password
     * @return
     */
    public UserLoginResult login(String sessionId, String auth, String password);

    public enum UserLogoffResult {
        SUCCESS
    }

    public UserLogoffResult logoff(String sessionId);

    public enum UserRegisterResult {
    }

    public UserRegisterResult register(UserRegister register, String passwordAgain);

    public void test();
}
