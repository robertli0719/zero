package robertli.zero.service.impl;

/**
 * This class is designed for instantiating EmailServiceImpl
 *
 * @version 1.0.1 version 2016-08-22
 * @author Robert Li
 */
public class EmailConfiguration {

    private String host;
    private String from;
    private String username;
    private String password;
    private int port;
    private boolean debug;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

}
