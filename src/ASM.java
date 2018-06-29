import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ASM {

    private static User currentUser;
    private static ArrayList<Asset> assets;
    private static ArrayList<User> users;
    private static ArrayList<RepairReport> repairs;

    public static void main(String[] args) {
        try {
            loadAllData();

        } catch (IOException e) {
            e.printStackTrace();
        }

        users.add(new User("ali","123456"));
        saveAllData();

    }

    private static void saveAllData() {
        try {
            User.saveUsers(users);
            Asset.saveAssets(assets);
//        saveReports();
        }
        catch (Exception e){
            e.getStackTrace();
        }

    }

    private static void loadAllData() throws IOException {
        users = User.loadUsers();
        assets = Asset.loadAssets();
//        RepairReport.loadRepairs();
    }







    public static User getCurrentUser() {
        return currentUser;
    }
}
