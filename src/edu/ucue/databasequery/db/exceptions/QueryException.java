package edu.ucue.databasequery.db.exceptions;

/**
 *
 * @author santos
 */
public class QueryException extends RuntimeException {

    public QueryException(String query) {
        super("Error al ejecutar la consulta: " + query);
    }
    
}
