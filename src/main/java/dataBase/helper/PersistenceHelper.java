/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase.helper;

import dataBase.annotations.database;
import configurations.Parameters;
import dataBase.dao.ConnectionFactory;
import dataBase.dao.interfaces.Persistence;
import dataBase.interfaces.DataBaseEntity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author connected
 * @param <T>
 */
public class PersistenceHelper<T extends DataBaseEntity> implements Persistence<T> {

    Class type;

    public PersistenceHelper(Class<T> type) {
        this.type = type;
    }

    @Override
    public Optional<T> insert(T entity) {
        PreparedStatement stmt = null;
        Connection connection = null;

        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(Parameters.schemaDataBase);
        sql.append(".");
        sql.append(createTableName(entity));
        sql.append("(");
        sql.append(getInsertableParametersName(entity));
        sql.append(")");
        sql.append(" Values(");
        sql.append(getParametersInsert(entity));
        sql.append(")");

        try {
            connection = new ConnectionFactory().getConnection();

            stmt = connection.prepareStatement(sql.toString());

            stmt = addParameters(stmt, entity, false, true);

            stmt.execute();
            stmt.close();

        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PersistenceHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<T> update(T entity) {
        PreparedStatement stmt = null;
        Connection connection = null;

        StringBuilder sql = new StringBuilder("update ");
        sql.append(Parameters.schemaDataBase);
        sql.append(".");
        sql.append(createTableName(entity));
        sql.append(" set ");
        sql.append(getParametersUpdate(entity));
        sql.append(" Where id = ?");

        try {
            connection = new ConnectionFactory().getConnection();

            System.out.println(sql.toString());

            stmt = connection.prepareStatement(sql.toString());

            stmt = addParameters(stmt, entity, true, true);

            stmt.execute();
            stmt.close();

        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PersistenceHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Optional.of(entity);
    }

    @Override
    public void remove(T entity) {
        PreparedStatement stmt = null;
        Connection connection = null;

        StringBuilder sql = new StringBuilder("delete from ");
        sql.append(Parameters.schemaDataBase);
        sql.append(".");
        sql.append(createTableName(entity));
        sql.append(" Where ");
        sql.append(getKeyParameter(entity));

        try {
            connection = new ConnectionFactory().getConnection();

            System.out.println(sql.toString());

            stmt = connection.prepareStatement(sql.toString());

            stmt = addParameters(stmt, entity, true, false);

            stmt.execute();
            stmt.close();

        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(PersistenceHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional<List<T>> select(String where) {
        PreparedStatement stmt = null;
        Connection connection = null;
        ResultSet resultSet;
        List<T> tlist = new LinkedList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("select * from ");
        sql.append(Parameters.schemaDataBase);
        sql.append(".");
        sql.append(type.getSimpleName().toLowerCase());
        sql.append(" ");
        sql.append(where);
        
        try {
            connection = new ConnectionFactory().getConnection();
            stmt = connection.prepareStatement(sql.toString());
            resultSet = stmt.executeQuery();

            tlist = createNewTObject(resultSet);
            
            stmt.close();

        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(PersistenceHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Optional.of(tlist);
    }

    @Override
    public <O> Optional<List<O>> select(Class dtoClass, String where) {
        PreparedStatement stmt = null;
        Connection connection = null;
        ResultSet resultSet;
        List<O> list = new LinkedList<>();
        StringBuilder sql = new StringBuilder();

        sql.append("select * from ");
        sql.append(Parameters.schemaDataBase);
        sql.append(".");
        sql.append(type.getSimpleName().toLowerCase());
        sql.append(" ");
        sql.append(where);
        
        try {
            connection = new ConnectionFactory().getConnection();
            stmt = connection.prepareStatement(sql.toString());
            resultSet = stmt.executeQuery();

            list = createNewObject(resultSet, dtoClass);
            
            stmt.close();

        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(PersistenceHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Optional.of(list);
    }

    private String createTableName(T entity) {
        return entity.getClass().getSimpleName().toLowerCase();
    }

    private String getInsertableParametersName(T entity) {
        StringBuilder names = new StringBuilder();

        for (Method method : entity.getClass().getDeclaredMethods()) {

            if (method.isAnnotationPresent(database.class)
                    && !method.getAnnotation(database.class).key()
                    && !method.isBridge()) {

                if (names.length() > 0) {
                    names.append(", ");
                }

                names.append(method.getName().replace("get", "").trim());
            }

        }

        return names.toString();
    }

    private String getParametersUpdate(T entity) {
        StringBuilder values = new StringBuilder();
        Arrays.asList(entity.getClass().getDeclaredMethods())
                .stream()
                .filter(method -> !method.isBridge())
                .filter(method -> method.isAnnotationPresent(database.class))
                .filter(method -> !method.getAnnotation(database.class).key())
                .forEach(method -> {

                    if (values.length() > 0) {
                        values.append(", ");
                    }

                    values.append(method.getName().replace("get", "").trim());
                    values.append(" = ?");
                });
        return values.toString();
    }

    private String getParametersInsert(T entity) {
        StringBuilder values = new StringBuilder();
        Long size = Arrays.stream(entity.getClass().getDeclaredMethods())
                .filter(method -> !method.isBridge())
                .filter(method -> method.isAnnotationPresent(database.class))
                .filter(method -> !method.getAnnotation(database.class).key())
                .count();

        for (int i = 0; i < size; i++) {
            if (i != 0) {
                values.append(", ");
            }
            values.append("?");
        }

        return values.toString();
    }

    private String getKeyParameter(T entity) {
        StringBuilder parameters = new StringBuilder();

        for (Method method : entity.getClass().getDeclaredMethods()) {

            if (method.isAnnotationPresent(database.class) 
                    && !method.getAnnotation(database.class).key() 
                    && method.isBridge()) {

                if (parameters.length() > 0) {
                    parameters.append(" and ");
                }

                parameters.append(method.getName().replace("get", "").trim());
                parameters.append(" = ?");
            }

        }

        return parameters.toString();
    }

    private PreparedStatement addParameters(PreparedStatement stmt, T entity, boolean addKeyParameters, boolean addNotKeyParameters) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int cont = 0;

        for (Method declaredMethod : entity.getClass().getDeclaredMethods()) {
            if (!declaredMethod.isBridge() && declaredMethod.isAnnotationPresent(database.class)
                    && !declaredMethod.getAnnotation(database.class).key() && addNotKeyParameters) {
            
                stmt.setObject(++cont, declaredMethod.invoke(entity));
            }
        }

        for (Method declaredMethod : entity.getClass().getDeclaredMethods()) {
            if (!declaredMethod.isBridge() && declaredMethod.isAnnotationPresent(database.class)
                    && declaredMethod.getAnnotation(database.class).key() && addKeyParameters) {
            
                stmt.setObject(++cont, declaredMethod.invoke(entity));
            }
        }

        return stmt;
    }

    private List<T> createNewTObject(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        List<T> auxList = new LinkedList<>();

        while (resultSet.next()) {
            T aux = (T) type.newInstance();
            
            for(Method method : type.getDeclaredMethods()) {
                
                if(method.isAnnotationPresent(database.class) && !method.isBridge()) {
                 
                    Method m = type.getDeclaredMethod(method.getName().replace("get", "set"), method.getReturnType());
                    m.invoke(aux, resultSet.getObject(method.getName().replace("get", "").toLowerCase()));
                        
                }
            }
            
            auxList.add(aux);
        }

        return auxList;
    }

    private <D> List<D> createNewObject(ResultSet resultSet, Class type) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        List<D> auxList = new LinkedList<>();

        while (resultSet.next()) {
            D aux = (D) type.newInstance();
            
            for(Method method : type.getDeclaredMethods()) {
                
                if(method.isAnnotationPresent(database.class) && !method.isBridge()) {
                 
                    Method m = type.getDeclaredMethod(method.getName().replace("get", "set"), method.getReturnType());
                    m.invoke(aux, resultSet.getObject(method.getName().replace("get", "").toLowerCase()));
                        
                }
            }
            
            auxList.add(aux);
        }

        return auxList;
    }
}