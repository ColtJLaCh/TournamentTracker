package LoginCredentials;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/* Login class, for dealing with any functionality related to usernames, passwords,
    and current databases.
 */

public class Login {
    private ArrayList<String> users;

    private HashMap<String, String> passwords = new HashMap<>();

    private HashMap<String, String> databases = new HashMap<>();

    private File[] userFile = {null, null, null};

    //Initialize the login page, update to include all users and user info.
    public Login() {
        this.users = new ArrayList<>();
        userFile[0] = new File("./src/main/java/LoginCredentials/accounts.txt");
        userFile[1] = new File("./src/main/java/LoginCredentials/databases.txt");
        userFile[2] = new File("./src/main/java/LoginCredentials/current_user.txt");
        updateUsers();
        updatePasswords();
        updateDatabases();
    }

    //Grab all users from the accounts file
    public void updateUsers() {
        try {
            Scanner scanner = new Scanner(userFile[0]);
            scanner.useDelimiter(",");
            while (scanner.hasNext()){
                users.add(scanner.next());
                scanner.next();
            }
        }
        catch (IOException ioException) {
            System.out.println("File Read Error");
        }
        //System.out.println(users);
    }

    //Grab all passwords and their respective user
    public void updatePasswords(){
        try {
            Scanner scanner = new Scanner(userFile[0]);
            scanner.useDelimiter(",");
            while (scanner.hasNext()){
                passwords.put(scanner.next(), scanner.next());
            }
        }
        catch (IOException ioException) {
            System.out.println("File Read Error");
        }
        //System.out.println(passwords);
    }

    //Grab all databases and their respective user
    public void updateDatabases(){
        try {
            Scanner scanner = new Scanner(userFile[1]);
            scanner.useDelimiter(",");
            while (scanner.hasNext()){
                databases.put(scanner.next(), scanner.next());
            }
        }
        catch (IOException ioException) {
            System.out.println("File Read Error");
        }
        //System.out.println(databases);
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    // Function to check password so we can keep the password map from being directly accessed.
    public boolean checkPassword(String username, String password){
        if(passwords.get(username).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    //Get all information tied to the user and store it in a file for use.
    public void loginUser(String username){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(userFile[2], false));
            out.write(username+","+ passwords.get(username) + "," +databases.get(username));
            out.flush();
            out.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
