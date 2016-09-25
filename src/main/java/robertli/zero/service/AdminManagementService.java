/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.service;

/**
 * This service is design for super administrators such as root user to manage
 * other administrators. super administrators can use this service to add,
 * delete, suspend, reset password, and and query any needed information for
 * other administrators.
 *
 * @see AdminService
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
public interface AdminManagementService {

    /**
     * add a new administrator to the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username of new administrator
     * @param orginealPassword the initial password for the new administrator
     * @return true if fail
     */
    public boolean addAdmin(String sessionId, String username, String orginealPassword);

    /**
     * delete an administrator from the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username of the administrator who will be delete
     * @return true if fail
     */
    public boolean deleteAdmin(String sessionId, String username);

    /**
     * the administrator who is suspended can't login the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username for the administrator who will be processed
     * @param suspended true if we want the administrator should be suspended
     * @return true if fail
     */
    public boolean setSuspendStatus(String sessionId, String username, boolean suspended);

    /**
     *
     * @param sessionId the identification of a web browser session
     * @param username the username for the administrator who will be processed
     * @param newPassword the new password for the administrator
     * @return true if fail
     */
    public boolean resetPassword(String sessionId, String username, String newPassword);
}
