import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

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
        viewAllAssets();

    }

    private static User findUser(String username) {
        for (User user:users)
            if (user.getUserName().toLowerCase().equals(username.toLowerCase()))
                return user;
        return null;
    }

    private static void signIn() throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        User user;

        System.out.println("Welcome to SUTech Asset Manager 1.0 ");
        System.out.print("Username: ");

        while ((user = findUser(input.nextLine())) == null)
            System.out.print("Username not found. Try again: ");

        while (user.getPassword().equals(User.sha256(input.nextLine())))
            System.out.println("Incorrect password. Try again");

        ASM.currentUser = user;
    }

    private static void showMenu() {

        if (currentUser.getIsAdmin())
            showAdminMenu();
        showUserMenu();

    }

    private static void showAdminMenu() {

        Scanner input = new Scanner(System.in);
        int choice;
        do {
            choice = input.nextInt();
            System.out.println("1- View All Assets");
            System.out.println("2- Repair an Asset");
            System.out.println("3- Add an Asset");
            System.out.println("4- Edit an Asset");
            System.out.println("5- Add a User");
            System.out.println("6- Edit a User");
            System.out.println("7- Change Password");
            System.out.println("8- Retire an Asset");
            System.out.println("9- Reports");
            System.out.println("10- Exit");
            System.out.print("Enter your choice: ");
        }
        while (choice < 1 || choice > 8);

        choose(choice);

    }

    private static void showUserMenu() {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            choice = input.nextInt();
            System.out.println("1- View All Assets");
            System.out.println("2- Repair an Asset");
            System.out.println("3- Add an Asset");
            System.out.println("4- Edit an Asset");
            System.out.println("5- Change Password");
            System.out.println("6- Retire an Asset");
            System.out.println("7- Reports");
            System.out.println("8- Exit");
        }
        while (choice < 1 || choice > 8);
        choose(choice);

    }

    private static void choose(int choice){
        switch (choice){
            case 1:
                viewAllAssets();
            case 2:
//                fillReport();
            case 3:
//                addAsset();
            case 4:
//                editAsset();
            case 5:
//                changePassword();
            case 6:
//                retireAsset();
            case 7:
//                displayReports();
            case 8:
                saveAllData();
                System.exit(0);
        }

    }

    private static void viewAllAssets() {
        for(Asset asset:assets) {
            System.out.println(asset);
        }
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
