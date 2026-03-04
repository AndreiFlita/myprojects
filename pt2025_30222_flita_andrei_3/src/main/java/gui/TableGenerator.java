package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

public class TableGenerator {
    /**
     * Creates a non-editable JTable from a list of objects using reflection.
     * <p>
     * The method reads all fields of the object's class to generate column headers
     * and fills each row with field values from the objects in the list.
     *
     * @param objects the list of objects to display in the table; must be non-null and non-empty,
     *                and all objects must be of the same class
     * @return a JTable representing the objects' data; empty table if list is null or empty
     */
    public static JTable createTable(List<?> objects) {
        if (objects == null || objects.isEmpty()) {
            return new JTable();
        }

        Class<?> objectClass = objects.get(0).getClass();
        Field[] fields = objectClass.getDeclaredFields();
        String[] columnNames = getColumnNames(objectClass);

        Object[][] data = new Object[objects.size()][fields.length];
        for (int i = 0; i < objects.size(); i++) {
            Object obj = objects.get(i);
            for (int j = 0; j < fields.length; j++) {
                try {
                    fields[j].setAccessible(true);
                    data[i][j] = fields[j].get(obj);
                } catch (IllegalAccessException e) {
                    data[i][j] = "N/A";
                }
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        return new JTable(model);
    }
    /**
     * Extracts the names of all declared fields in the specified class.
     * <p>
     * This method uses reflection to get the field names of the given class
     * and returns them as a String array, which can be used as table headers.
     *
     * @param clazz the class whose declared field names are to be retrieved
     * @return a String array containing the names of all declared fields in the class
     */
    public static String[] getColumnNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }
        return columnNames;
    }
}

