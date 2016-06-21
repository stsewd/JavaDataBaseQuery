package edu.ucue.databasequery.db;

import edu.ucue.databasequery.db.exceptions.QueryException;
import edu.ucue.databasequery.db.exceptions.CloseConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author santos
 */
public class DataBase {
    private final Connection connection;
    
    public DataBase(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Object o) {
        Statement stmt = null;
        String query = null;
        try {
            query = Query.insert(o);
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new QueryException(query);
        } finally {
            closeConnection(stmt);
        }
    }

    private void closeConnection(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException ex) {
            throw new CloseConnectionException();
        }
    }
    
}
