package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AbstractDAO<T> {
    public static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     *
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");

        return sb.toString();
    }

    private String createSelectAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createInsertQuery(T obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append(type.getSimpleName());

        sb.append("(");
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String st = field.getName() + ", ";
            sb.append(st);
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(")");

        sb.append(" VALUES (");
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            sb.append(" ?, ");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(")");
        return sb.toString();
    }

    private String createUpdateQuery(T obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equals("id")) {
                String st = field.getName() + "=?, ";
                sb.append(st);
            }
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("WHERE id = ?");
        return sb.toString();
    }

    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAll();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findALL " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
//                    if (type == Orders.class && fieldName.equals("id"))
//                        continue;
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);

                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(t) instanceof String) {
                        statement.setString(i++, (String) field.get(t));
                    } else if (field.get(t) instanceof Float) {
                        statement.setFloat(i++, (Float) field.get(t));
                    } else
                        statement.setInt(i++, (Integer) field.get(t));

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int i = 1, id = -1;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id"))
                    try {
                        if (field.get(t) instanceof String) {
                            statement.setString(i++, (String) field.get(t));
                        } else if (field.get(t) instanceof Float) {
                            statement.setFloat(i++, (Float) field.get(t));
                        } else
                            statement.setInt(i++, (Integer) field.get(t));

                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                else
                    id = (int) field.get(t);
            }
            statement.setInt(i++, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }


    public T deleteById(int id) {
        T t = findById(id);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }


    public JTable createTable(List<T> objects, int ok) { //ok=0 C AND P ; ok=1 O
        List<String> list = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (ok == 1 && field.getName().equals("stock"))
                continue;
            if (!field.getName().equals("id") && !field.getName().equals("idClient") && !field.getName().equals("idProduct"))
                try {
                    list.add(field.getName());

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
        }

        String[] columnNames = new String[list.size()];
        list.toArray(columnNames);

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (Object elem : objects) {
            List<Object> row = new ArrayList<>();
            for (Field field : elem.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value;
                if (!field.getName().equals("id") && !field.getName().equals("idClient") && !field.getName().equals("idProduct"))
                    try {
                        value = field.get(elem);
                        row.add(value);

                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

            }
            Object[] data = new Object[row.size()];
            row.toArray(data);
            model.addRow(data);

        }
        return table;
    }


    public Class<T> getType() {
        return type;
    }
}
