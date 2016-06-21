package edu.ucue.databasequery.db;
import java.lang.reflect.Field;
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
}
