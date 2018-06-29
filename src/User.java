import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private static int lastID = 0;
    private int ID;
    private String userName;
    private String password;

    User(String userName, String password) {
        try {
            this.ID = ++lastID;
            this.userName = userName;
            this.password = sha256(password);
        }
        catch (Exception e){
            e.getStackTrace();
//            this.setPassword();
        }
    }

    private User(String userName, String password, int id){
        this.userName = userName;
        this.password = password;
        this.ID = id;
        lastID = ID;
    }

    static void saveUsers(ArrayList<User> users){

    }

    static ArrayList<User> loadUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("DataBase\\Users"));
        String line;
        while ((line = reader.readLine()) != null){
            users.add(new User(line.split(":")[1],line.split(":")[2],Integer.parseInt(line.split(":")[0])));
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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return String.format("%d:%s:%s",
                getID(), getUserName(), getPassword());
    }
}
