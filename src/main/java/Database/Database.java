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
                //String basicTable = "BasicTable";
                String[] extras = {"Wins", "Losses"};


                //createTable(basicTable,2, extras, connection);

                //Add a player to table
                String[] player = {"Chicago 25ers", "6", "4"};

                //addToTable(basicTable, player, connection);

                //Update an existing player's values
                String[] changes = {"Wins"};
                String[] newValues = {"7"};

                //updatePlayer(basicTable, 3, changes,newValues, connection);

                //Rertrieve table rows
                //ResultSet basicTourney = getTable(basicTable, connection);


                //Retrieve specific table rows
                //ResultSet tourneyWinners = curateTable(basicTable, "Wins > 6", connection);


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

    public boolean createTable(String tableName, int sets, String[] extraColumns,
                             Connection connection) throws SQLException {
        String sqlCreate = "CREATE TABLE `" + tableName + "` (" +
                "ID" + " INT NOT NULL AUTO_INCREMENT, " +
                "Sets" + " INT DEFAULT '" + sets + "'," +
                "Player" + " VARCHAR(50), " +
                "TeamName" + " VARCHAR(50), ";
        for (int i = 0; i < extraColumns.length; i++) {
            sqlCreate = sqlCreate + extraColumns[i] + " VARCHAR(50) DEFAULT '0', ";
        }
        sqlCreate = sqlCreate + "PRIMARY KEY(" + "ID" + ")" + ");";

        //System.out.println(sqlCreate);

        Statement createTable;
        //Get database information
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            System.out.println("A tournament with the name " + tableName + " already exists!");
            return false;
        }
        else{
            createTable = connection.createStatement();
            createTable.execute(sqlCreate);
            System.out.println("The " + tableName + " tournament has been created.");
            return true;
        }
    }


    public boolean addToTable(String tableName, String[] newPlayer,String teamName, Connection connection) throws SQLException {
        String sqlInsert = "INSERT INTO `" + tableName + "` (Player, TeamName) VALUES ('" + newPlayer[0] + "','" + teamName + "')";

        for (int i = 1; i < newPlayer.length; i++) {
            sqlInsert = sqlInsert  + ", ('"+ newPlayer[i] + "','" + teamName + "')";
        }
        sqlInsert = sqlInsert + ";";

        //System.out.println(sqlInsert);
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement add2Table = connection.createStatement();
            add2Table.execute(sqlInsert);
            System.out.println("Added player to tournament " + tableName);
            return true;
        } else {
            System.out.println("Error: Tournament doesn't exist.");
            return false;
        }
    }

    public boolean updatePlayer(String tableName, int playerID, String[] changingValues,
                              String[] newValues, Connection connection ) throws SQLException {
        String sqlUpdate = "UPDATE `" + tableName + "` SET " + changingValues[0] + " = '" + newValues[0] + "'";

        for (int i = 1; i < changingValues.length; i++) {
            sqlUpdate = sqlUpdate + ", " + changingValues[i] + " = '" + newValues[i] + "'";
        }
        sqlUpdate = sqlUpdate + " WHERE ID = " + playerID + ";";

        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement updateTable = connection.createStatement();
            updateTable.execute(sqlUpdate);
            System.out.println("Updated player info");
            return true;
        } else {
            System.out.println("Error: Tournament doesn't exist.");
            return false;
        }
    }

    public ResultSet getTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dataTable = connection.createStatement();
            ResultSet tournament = dataTable.executeQuery("SELECT * FROM `" + tableName + "`");

            while (tournament.next()){
                System.out.println(tournament.getInt("ID") + ", " +tournament.getString("Player"));
            }
            return tournament;
        } else {
            System.out.println("Error: Tournament doesn't exist.");
            return null;
        }
    }

    public ResultSet curateTable(String tableName, String condition, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dataTable = connection.createStatement();
            ResultSet tournament = dataTable.executeQuery("SELECT * FROM `" + tableName + "` WHERE " + condition);

            while (tournament.next()){
                System.out.println(tournament.getInt("ID") + ", " +tournament.getString("Player"));
            }
            return tournament;
        } else {
            System.out.println("Error: Tournament doesn't exist.");
            return null;
        }
    }


    public boolean dropTable(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        //Looking for the table with tableName
        ResultSet resultSet = md.getTables(DBConst.DB_NAME,
                null, tableName, null);
        //If the table is present
        if(resultSet.next()){
            Statement dropTable = connection.createStatement();
            dropTable.execute("DROP TABLE `" + tableName + "`");
            System.out.println("Successfully deleted tournament " + tableName);
            return true;
        } else {
            System.out.println("Error: Tournament doesn't exist.");
            return false;
        }
    }
}
