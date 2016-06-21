package edu.ucue.databasequery.db.exceptions;

/**
 *
 * @author santos
 */
public class ConnectionException extends RuntimeException {

    public ConnectionException(String dbUrl) {
        super("No se pudo establecer la conexión a: " + dbUrl);
    }
    
}
