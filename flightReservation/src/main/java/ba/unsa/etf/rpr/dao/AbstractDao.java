package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.exceptions.FlightBookingException;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.*;


/**
 * Abstract class that implements core DAO CRUD methods for every entity
 * @param <T> the type of objects this DAO can handle
 * @author Safet Čomor
 */

public abstract class AbstractDao <T extends Idable> implements Dao<T> {
    /**
     *Connection instance variable to represent the database connection.
     */
    private static Connection connection = null;
    /**
    *String instance variable to represent the name of the table associated with the entity.
    */
    private String tableName;
    /**
     * Constructor that sets the tableName and creates the database connection.
     * @param tableName The name of the table associated with the entity.
     */
    public AbstractDao(String tableName) {
        this.tableName = tableName;
        createConnection();
    }
    /**
     * Method that creates a database connection if it does not already exist.
     */
    private static void createConnection(){
        if(AbstractDao.connection==null) {
            try {
                Properties p = new Properties();
                p.load(ClassLoader.getSystemResource("application.properties.sample").openStream());
                String url = p.getProperty("db.connection_string");
                String username = p.getProperty("db.username");
                String password = p.getProperty("db.password");
                AbstractDao.connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }finally {
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    @Override
                    public void run(){
                        try{
                            connection.close();
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * Method that returns the database connection.
     * @return The database connection.
     */
    public static Connection getConnection() {
        return AbstractDao.connection;
    }

    /**
     * Abstract method that converts a row from a result set to an object.
     * @param rs The result set from the database.
     * @return The object representation of the result set.
     * @throws FlightBookingException if there was a problem converting the row to an object
     */
    public abstract T row2object(ResultSet rs) throws FlightBookingException;
    /**
     * Abstract method that converts an object to a row for the database.
     * @param object The object that is being converted.
     * @return The database row representation of the object.
     */
    public abstract Map<String, Object> object2row(T object) ;
    /**
     * Method that retrieves an object from the database by its id.
     * @param id The id of the object.
     * @return The object with the specified id.
     * @throws FlightBookingException if there was a problem retrieving the object from the database
     */
    public T getById(int id) throws FlightBookingException {
        return executeQueryUnique("SELECT * FROM "+ this.tableName +" WHERE id = ?",new Object[]{id});
    }
    /**
     * Returns a list of all items from the table.
     * @return The list of all items from the table.
     * @throws FlightBookingException if an error occurs during the operation.
     */
    public List<T> getAll() throws FlightBookingException {
        return executeQuery("SELECT * FROM " + tableName, null);
    }
    /**
     * Deletes an item from the table with a specified ID.
     * @param id The ID of the item to be deleted.
     * @throws FlightBookingException if an error occurs during the operation.
     */
    public void delete(int id) throws FlightBookingException {
        String sql = "DELETE FROM "+tableName+" WHERE id = ?";
        try{
            PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new FlightBookingException(e.getMessage(), e);
        }
    }
    /**
     * Adds a new item to the table.
     * @param item The item to be added to the table.
     * @return The item with the assigned ID from the table.
     * @throws FlightBookingException if an error occurs during the operation.
     */
    public T add(T item) throws FlightBookingException{
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName);
        builder.append(" (").append(columns.getKey()).append(") ");
        builder.append("VALUES (").append(columns.getValue()).append(")");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            // bind params. IMPORTANT treeMap is used to keep columns sorted so params are bind correctly
            int counter = 1;
            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue; // skip ID
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next(); // we know that there is one key
            item.setId(rs.getInt(1)); //set id to return it back */

            return item;
        }catch (SQLException e){
            throw new FlightBookingException(e.getMessage(), e);
        }
    }
    /**
     * Updates an item in the database.
     *
     * @param item the item to be updated
     * @return the updated item
     * @throws FlightBookingException if there is an error in the database operation
     */
    public T update(T item) throws FlightBookingException{
        Map<String, Object> row = object2row(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(tableName)
                .append(" SET ")
                .append(updateColumns)
                .append(" WHERE id = ?");

        try{
            PreparedStatement stmt = getConnection().prepareStatement(builder.toString());
            int counter = 1;
            for (Map.Entry<String, Object> entry: row.entrySet()) {
                if (entry.getKey().equals("id")) continue; // skip ID
                stmt.setObject(counter, entry.getValue());
                counter++;
            }
            stmt.setObject(counter, item.getId());
            stmt.executeUpdate();
            return item;
       }catch (SQLException e){
            throw new FlightBookingException(e.getMessage(), e);
        }
    }

    /**
     * Utility method for executing any kind of query
     * @param query - SQL query
     * @param params - params for query
     * @return List of objects from database
     * @throws FlightBookingException in case of error with db
     */

    public List<T> executeQuery(String query, Object[] params) throws FlightBookingException{
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            if (params != null){
                for(int i = 1; i <= params.length; i++){
                    stmt.setObject(i, params[i-1]);
                }
            }
            ResultSet rs = stmt.executeQuery();
            ArrayList<T> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(row2object(rs));
            }
            return resultList;
        } catch (SQLException e) {
            throw new FlightBookingException(e.getMessage(), e);
        }
    }

    /**
     * Utility for query execution that always return single record
     * @param query - query that returns single record
     * @param params - list of params for sql query
     * @return Object
     * @throws FlightBookingException in case when object is not found
     */

    public T executeQueryUnique(String query, Object[] params) throws FlightBookingException{
        List<T> result = executeQuery(query, params);
        if (result != null && result.size() == 1){
            return result.get(0);
        }else{
            throw new FlightBookingException("Object not found");
        }
    }


    /**
     * Accepts KV storage of column names and return CSV of columns and question marks for insert statement
     * Example: (id, name, date) ?,?,?
     */
    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) continue; //skip insertion of id due autoincrement
            columns.append(entry.getKey());
            questions.append("?");
            if (row.size() != counter) {
                columns.append(",");
                questions.append(",");
            }
        }
        return new AbstractMap.SimpleEntry<String,String>(columns.toString(), questions.toString());
    }

    /**
     * Prepare columns for update statement id=?, name=?, ...
     * @param row
     * @return
     */

    private String prepareUpdateParts(Map<String, Object> row){
        StringBuilder columns = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry: row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) continue; //skip update of id due where clause
            columns.append(entry.getKey()).append("= ?");
            if (row.size() != counter) {
                columns.append(",");
            }
        }
        return columns.toString();
    }

}

