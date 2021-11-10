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
                String[] extras = {"Wins", "Losses"};

                createTable(basicTable, extras, connection);

                //Add a player to table
                String[] player = {"Chicago 25ers", "6", "4"};

                //addToTable(basicTable, player, connection);

                //Rertrieve table rows
                getTable(basicTable, connection);

                
                //Drop the basic table
                //dropTable(basicTable, connection);
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

    private void createTable(String tableName, String[] extraColumns,
                             Connection connection) throws SQLException {
        String sqlCreate = "CREATE TABLE `" + tableName + "` (" +
                "ID" + " int NOT NULL AUTO_INCREMENT, " +
                "Player" + " VARCHAR(50), ";
        for (int i = 0; i < extraColumns.length; i++) {
            sqlCreate = sqlCreate + extraColumns[i] + " VARCHAR(50), ";
        }
        sqlCreate = sqlCreate + "PRIMARY KEY(" + "ID" + ")" + ");";

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
            createTable.execute(sqlCreate);
            System.out.println("The " + tableName + " tournament has been created.");
        }
    }

    private void dropTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables("nburrowsjava",
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dropTable = connection.createStatement();
            dropTable.execute("DROP TABLE `" + tableName + "`");
            System.out.println("Successfully deleted tournament " + tableName);
        } else {
            System.out.println("Error: Tournament doesn't exist.");
        }
    }

    private void addToTable(String tableName, String[] newPlayer, Connection connection) throws SQLException {
        String sqlInsert = "INSERT INTO `" + tableName + "` VALUES (0, '"+newPlayer[0] + "'";

        for (int i = 1; i < newPlayer.length; i++) {
            sqlInsert = sqlInsert  + ", '" + newPlayer[i]+ "'";
        }
        sqlInsert = sqlInsert + ");";

        System.out.println(sqlInsert);
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables("nburrowsjava",
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dropTable = connection.createStatement();
            dropTable.execute(sqlInsert);
            System.out.println("Added player to tournament " + tableName);
        } else {
            System.out.println("Error: Tournament doesn't exist.");
        }
    }

    private void getTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables("nburrowsjava",
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dropTable = connection.createStatement();
            dropTable.execute("SELECT * FROM `" + tableName + "`");

        } else {
            System.out.println("Error: Tournament doesn't exist.");
        }
    }
}
