package edu.ucue.databasequery.db.exceptions;

import java.sql.SQLException;

/**
 *
 * @author santos
 */
public class AddHeaderException extends RuntimeException {

    public AddHeaderException(SQLException ex) {
        super("Error al agregar cabecera");
    }
    
}
