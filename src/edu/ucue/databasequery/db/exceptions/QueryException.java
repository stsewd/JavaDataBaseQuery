package edu.ucue.databasequery.db.exceptions;

import java.sql.SQLException;

/**
 *
 * @author santos
 */
public class QueryException extends RuntimeException {
    private final SQLException ex;
    public QueryException(String query, SQLException ex) {
        super("Error al ejecutar la consulta: " + query);
        this.ex = ex;
    }

    public SQLException getEx() {
        return ex;
    }    
}
