package edu.ucue.databasequery.db.exceptions;

import java.sql.SQLException;

/**
 *
 * @author santos
 */
public class AddBodyException extends RuntimeException {

    public AddBodyException(SQLException ex) {
        super("Error al agregar cuerpo de la consulta.");
    }
    
}
