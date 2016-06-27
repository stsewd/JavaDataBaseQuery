package edu.ucue.databasequery.db;

import edu.ucue.databasequery.db.exceptions.AddBodyException;
import edu.ucue.databasequery.db.exceptions.AddHeaderException;
import edu.ucue.databasequery.db.exceptions.QueryException;
import edu.ucue.databasequery.db.exceptions.CloseConnectionException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

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
    
    public void addElement(String tableName, Map<String, String> fields) {
        Statement stmt = null;
        String query = null;
        try {
            query = Query.insert(tableName, fields);
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
            datos.add(getHeaderColName(result));
            addBody(datos, result);
            return datos;
        } catch (SQLException ex) {
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public ArrayList<ArrayList<String>> getAll(String tableName) {
        String query = Query.selectAll(tableName);
        ArrayList<ArrayList<String>> datos;
        datos = consult(query);
        return datos;
    }
    
    private ArrayList<String> getHeaderColName(ResultSet result) {
        try {
            ArrayList<String> columna = new ArrayList<>();
            int numCols = result.getMetaData().getColumnCount();
            for (int i = 0; i < numCols; i++)
                columna.add(result.getMetaData().getColumnName(i + 1));
            return columna;
        } catch (SQLException ex) {
            throw new AddHeaderException(ex);
        }
    }
    
    private void addBody(ArrayList<ArrayList<String>> datos, ResultSet result) {
        try {
            int numCols = result.getMetaData().getColumnCount();
            ArrayList<String> columna;
            while (result.next()) {
                columna = new ArrayList<>();
                for (int i = 0; i < numCols; i++)
                    columna.add(result.getString(i + 1));
                datos.add(columna);
            }
        } catch (SQLException ex) {
            throw new AddBodyException(ex);
        }
    }
    
    private ArrayList<ArrayList<String>> consultWithoutHeader(String query) {
        Statement stmt = null;
        ArrayList<ArrayList<String>> datos = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            addBody(datos, result);
            return datos;
        } catch (SQLException ex) {
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public void update(String query) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            throw new QueryException(query, ex);
        } finally {
            closeConnection(stmt);
        }
    }
    
    public void delete(String tableName, ArrayList<DBField> fields) {
        String query = Query.delete(tableName, fields);
        query(query);
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
        String query = Query.allTableNames();
        ArrayList<ArrayList<String>> result = this.consultWithoutHeader(query);
        for (ArrayList<String> a : result)
            names.add(a.get(0));
        return names;
    }
    
    public ArrayList<String> getFields(String tableName) {
        ArrayList<String> fields = new ArrayList<>();
        String query = Query.allFields(tableName);
        ArrayList<ArrayList<String>> result = this.consultWithoutHeader(query);
        for (ArrayList<String> a : result)
            fields.add(a.get(0));
        return fields;
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
