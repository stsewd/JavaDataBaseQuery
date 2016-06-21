package edu.ucue.databasequery.db.exceptions;

/**
 *
 * @author santos
 */
public class CloseConnectionException extends RuntimeException {

    public CloseConnectionException() {
        super("Error al cerrar la base de datos.");
    }
    
}
