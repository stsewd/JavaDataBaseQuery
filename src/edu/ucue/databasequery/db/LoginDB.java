package edu.ucue.databasequery.db;

import java.util.Properties;

/**
 *
 * @author santos
 */
public class LoginDB {
    private final String dbUrl;
    private String user;
    private String password;

    public LoginDB(String dbUrl) {
        this.dbUrl = dbUrl;
        this.user = null;
        this.password = null;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);
        return properties;
    }
}
