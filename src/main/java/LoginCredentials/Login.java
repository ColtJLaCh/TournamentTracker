package LoginCredentials;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private ArrayList<String> users = new ArrayList<>();

    private HashMap<String, String> passwords = new HashMap<>();

    private File[] userFile = {null};

    public Login() {
        this.users = new ArrayList<>();
        userFile[0] = new File("./src/main/java/LoginCredentials/accounts.txt");
        updateUsers();
        updatePasswords();
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
        System.out.println(users);
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
        System.out.println(passwords);
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}
