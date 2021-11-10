package Database;
import java.sql.*;

public class Database {
    private static Database instance;
    private Connection connection = null;

    private Database(){
        //Database connection
        if(connection == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection =
                        DriverManager.getConnection("jdbc:mysql://localhost/"
                                        + DBConst.DB_NAME + "?serverTimezone=UTC",
                                DBConst.DB_USER,
                                DBConst.DB_PASS);
                System.out.println("Created Connection");
                //Create a basic table
                String basicTable = "BasicTable";
                String sqlCreate = "CREATE TABLE " + basicTable + " (" +
                        "ID" + " int NOT NULL AUTO_INCREMENT, " +
                        "Player" + " VARCHAR(50), " +
                        "PRIMARY KEY(" + "ID" + ")" +
                        ");";
                createTable(basicTable,sqlCreate, connection);
                dropTable(basicTable, connection);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close(){
        System.out.println("Closing connection");
        try{
            connection.close();
        }
        catch (Exception e){
            connection = null;
            e.printStackTrace();
        }
    }

    private void createTable(String tableName, String tableQuery,
                             Connection connection) throws SQLException {
        Statement createTable;
        //Get database information
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables("nburrowsjava",
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            System.out.println("A tournament with the name " + tableName + " already exists!");
        }
        else{
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println("The " + tableName + " table has been inserted");
        }
    }

    private void dropTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables("nburrowsjava",
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            //System.out.println("A tournament with the name " + tableName + " already exists!");
            Statement dropTable = connection.createStatement();
            dropTable.execute("DROP TABLE " + tableName);
            System.out.println("Successfully deleted tournament " + tableName);
        } else {
            System.out.println("Error: Tournament doesn't exist.");
        }
    }
}
