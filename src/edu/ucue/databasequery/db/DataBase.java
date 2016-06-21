package edu.ucue.databasequery.db;

import edu.ucue.databasequery.db.exceptions.QueryException;
import edu.ucue.databasequery.db.exceptions.CloseConnectionException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public ArrayList<ArrayList<String>> consult(String query) {
        Statement stmt = null;
        ArrayList<ArrayList<String>> datos = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            int numCols = result.getMetaData().getColumnCount();
            ArrayList<String> columna = new ArrayList<>();
            for (int i = 0; i < numCols; i++)
                columna.add(result.getMetaData().getColumnName(i + 1));
            datos.add(columna);
            while (result.next()) {
                columna = new ArrayList<>();
                for (int i = 0; i < numCols; i++)
                    columna.add(result.getString(i + 1));
                datos.add(columna);
            }
            return datos;
        } catch (SQLException ex) {
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public void query(String query) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public ArrayList<String> getAllTablesNames() {
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT TABLE_NAME FROM USER_TABLES";
        ArrayList<ArrayList<String>> result = this.consult(query);
        for (ArrayList<String> a : result.subList(1, result.size()))
            names.add(a.get(0));
        return names;
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
