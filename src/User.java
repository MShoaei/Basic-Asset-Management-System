import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private static int lastID = 0;
    private int ID;
    private String userName;
    private String password;
    private boolean isAdmin;

    User(String userName, String password, boolean createAdmin) {
        try {
            if (createAdmin && ASM.getCurrentUser().getIsAdmin()){
                this.isAdmin = true;
            }
            this.ID = ++lastID;
            this.userName = userName;
            this.password = sha256(password);
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

    private User(String userName, String password, int id, boolean isAdmin){
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.ID = id;
        lastID = ID;
    }

    void edit(){
        try {
            setUserName();
            setPassword();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    void setPassword() throws NoSuchAlgorithmException{
        Scanner input = new Scanner(System.in);
        String password;
        while (true) {
            System.out.print("Enter new password: ");
            password = sha256(input.nextLine());
            System.out.print("Re-type password: ");
            if (sha256(input.nextLine()).equals(password)) {
                this.password = password;
                break;
            }
            else
                System.out.println("Passwords did not match.");
        }

    }

    static void saveUsers(ArrayList<User> users) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase\\UsersWrite"));
        for(User user : users){
            writer.write(user.toString() + "\n");
        }
        writer.close();
    }

    static ArrayList<User> loadUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("DataBase\\Users"));
        String line;
        boolean isAdmin;
        while ((line = reader.readLine()) != null){
            isAdmin =  Boolean.parseBoolean(line.split(":")[3]);
            users.add(new User(line.split(":")[1],line.split(":")[2], Integer.parseInt(line.split(":")[0]), isAdmin));
        }
        return users;
    }

    static String sha256(String input) throws NoSuchAlgorithmException {

        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b: result)
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

        return sb.toString();
    }

    public int getID() {
        return ID;
    }

    private void setUserName(){
        Scanner input = new Scanner(System.in);
        String username;
        System.out.print("New username: ");
        username = input.nextLine();
        while (ASM.findUserByName(username) != null){
            System.out.println("Username already exists. Try another username: ");
            username = input.nextLine();
        }
        this.userName = username;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return String.format("%d:%s:%s:%s",
                getID(), getUserName(), getPassword(), getIsAdmin());
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
}
