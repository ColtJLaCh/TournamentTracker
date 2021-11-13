package LoginCredentials;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private ArrayList<String> users;

    private HashMap<String, String> passwords = new HashMap<>();

    private HashMap<String, String> databases = new HashMap<>();

    private File[] userFile = {null, null, null};

    public Login() {
        this.users = new ArrayList<>();
        userFile[0] = new File("./src/main/java/LoginCredentials/accounts.txt");
        userFile[1] = new File("./src/main/java/LoginCredentials/databases.txt");
        userFile[2] = new File("./src/main/java/LoginCredentials/current_user.txt");
        updateUsers();
        updatePasswords();
        updateDatabases();
    }

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

    public boolean checkPassword(String username, String password){
        if(passwords.get(username).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

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
