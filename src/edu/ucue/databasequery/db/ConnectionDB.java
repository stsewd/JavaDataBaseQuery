package edu.ucue.databasequery.db;

import edu.ucue.databasequery.db.exceptions.ConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author santos
 */
public class ConnectionDB {
    public static Connection getConnection(LoginDB login) {
        try {
            Connection connection;
            connection = DriverManager.getConnection(login.getDbUrl(), login.getProperties());
            return connection;
        } catch (SQLException ex) {
            throw new ConnectionException(login.getDbUrl());
        }
    }
}
