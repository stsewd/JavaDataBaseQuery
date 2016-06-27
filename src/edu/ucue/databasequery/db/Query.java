package edu.ucue.databasequery.db;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;

/**
 *
 * @author santos
 */
public class Query {
    public static String insert(Object o) {
        Set<Entry<String, String>> object_values = getValues(o).entrySet();
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");
        
        for (Entry<String, String> entry : object_values) {
            fields.add(entry.getKey());
            values.add(entry.getValue());
        }
        
        String query;
        query = "INSERT INTO " + getName(o) + " " +
                "(" + fields.toString() + ")" + " " +
                "VALUES(" + values.toString() + ")";
        return query;
    }

    private static String getName(Object o) {
        return o.getClass().getName();
    }

    private static Map<String, String> getValues(Object o) {
        Map<String, String> values = new LinkedHashMap<>();
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (value != null)
                    values.put(field.getName(), value.toString());
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // Todo esta bien
        }
        return values;
    }

    public static String insert(String tableName, Map<String, String> fieldsMap) {
        StringJoiner fields = new StringJoiner(",");
        StringJoiner values = new StringJoiner(",");
        for (Entry<String, String> entry : fieldsMap.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                fields.add(entry.getKey());
                values.add("'" + entry.getValue() + "'");
            }
        }
        String query;
        query = "INSERT INTO " + tableName + " " +
                "(" + fields.toString() + ")" + " " +
                "VALUES(" + values.toString() + ")";
        return query;
    }

    static String selectAll(String tableName) {
        String query;
        query = "SELECT * FROM " + tableName;
        return query;
    }

    public static String delete(String tableName, ArrayList<DBField> fields) {
        String query;
        query = "DELETE FROM" + " " + tableName + " " +
                "WHERE" + " " + getCondition(fields); 
        return query;
    }

    static String allFields(String tableName) {
        String query;
        query = "SELECT" + " " + "COLUMN_NAME" + " " +
                "FROM" + " " + "USER_TAB_COLUMNS" + " " +
                "WHERE" + " " + "TABLE_NAME" + "=" + "'" + tableName + "'";
        return query;
    }

    static String allTableNames() {
        String query;
        query = "SELECT" + " " + "TABLE_NAME" + " " +
                "FROM" + " " + "USER_TABLES";
        return query;
    }

    private static String getCondition(ArrayList<DBField> fields) {
        StringJoiner condition = new StringJoiner(" AND ");
        for (DBField field : fields)
            condition.add(field.getName() + "=" + "'" + field.getValue() + "'");
        return condition.toString();
    }

    static String update(String tableName, ArrayList<DBField> previewfields, ArrayList<DBField> newFields) {
        String query;
        query = "UPDATE" + " " + tableName + " " +
                "SET" + " " + getSet(newFields) + " " +
                "WHERE" + " " + getCondition(previewfields);
        return query;
    }

    private static String getSet(ArrayList<DBField> fields) {
        StringJoiner condition = new StringJoiner(", ");
        for (DBField field : fields)
            condition.add(field.getName() + "=" + "'" + field.getValue() + "'");
        return condition.toString();
    }
}
