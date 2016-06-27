package edu.ucue.databasequery.db;

/**
 *
 * @author santos
 */
public class DBField {
    private final String name;
    private final String value;

    public DBField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
