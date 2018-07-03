import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ASM {

    private static User currentUser;
    private static ArrayList<Asset> assets;
    private static ArrayList<User> users;
    private static ArrayList<RepairReport> reports;

    public static void main(String[] args) {
        try {
            loadAllData();
            signIn();
            while (true)
                showMenu();
        }
        catch (Exception e){
            e.getStackTrace();
        }
        finally {
            saveAllData();
        }
    }

    static User findUserByName(String username) {
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

        while ((user = findUserByName(input.nextLine())) == null)
            System.out.print("Username not found. Try again: ");

        System.out.print("Password: ");
        while (!user.getPassword().equals(User.sha256(input.nextLine())))
            System.out.print("Incorrect password. Try again: ");

        ASM.currentUser = user;
        System.out.println("Signed In!");;
    }

    private static void showMenu() {

        if (currentUser.getIsAdmin())
            showAdminMenu();
        else {
            showUserMenu();
        }


    }

    private static void showAdminMenu() {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
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
            choice = input.nextInt();
        }
        while (choice < 1 || choice > 10);

        choose(choice);

    }

    private static void showUserMenu() {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1- View All Assets");
            System.out.println("2- Repair an Asset");
            System.out.println("3- Add an Asset");
            System.out.println("4- Edit an Asset");
            System.out.println("5- Change Password");
            System.out.println("6- Retire an Asset");
            System.out.println("7- Reports");
            System.out.println("8- Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
        }
        while (choice < 1 || choice > 8);

        choose(choice);

    }

    private static void choose(int choice){
        if (currentUser.getIsAdmin()) {
            switch (choice){
                case 1:
                    viewAllAssets();
                    break;
                case 2:
                    fillReport();
                    break;
                case 3:
                    addAsset();
                    break;
                case 4:
                    editAsset();
                    break;
                case 5:
                    addUser();
                    break;
                case 6:
                    editUser();
                    break;
                case 7:
                    changePassword();
                    break;
                case 8:
                    retireAsset();
                    break;
                case 9:
                    displayReports();
                    break;
                case 10:
                    saveAllData();
                    System.out.println("Everything is saved.");
                    System.exit(0);
                    break;
            }
        }
        else {
            switch (choice) {
                case 1:
                    viewAllAssets();
                    break;
                case 2:
                    fillReport();
                    break;
                case 3:
                    addAsset();
                    break;
                case 4:
                    editAsset();
                    break;
                case 5:
                    changePassword();
                    break;
                case 6:
                    retireAsset();
                    break;
                case 7:
                    displayReports();
                    break;
                case 8:
                    saveAllData();
                    System.out.println("Everything is saved.");
                    System.exit(0);
                    break;
            }
        }
    }

    private static void displayReports() {
        Scanner input = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1- Find Asset by Model");
            System.out.println("2- ALL Repairs by User");
            System.out.println("3- All Users");
            System.out.println("4- Asset Repairs");
            System.out.println("5- Repairs by Date");
            System.out.println("6- Troubleshooting");
            System.out.println("7- Find Assets by Owner");
            System.out.println("8- Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
        }
        while (choice < 1 || choice > 8);

        chooseReportType(choice);
    }

    private static void chooseReportType(int choice) {
        switch (choice){
            case 1:
                findAssetByModel();
                break;
            case 2:
                findAssetByUser();
                break;
            case 3:
                displayAllUsers();
                break;
            case 4:
                displayAssetRepairs();
                break;
            case 5:
                findReportsByDate();
                break;
            case 6:
                findReportByCause();
                break;
            case 7:
                saveAllData();
                System.out.println("Everything is saved!");
                System.exit(0);

        }
    }

    private static void findReportsByDate() {
        System.out.print("Find reports from (YYYY-MM-DD): ");
        LocalDate fromDate = LocalDate.parse(new Scanner(System.in).nextLine());
        System.out.print("to (YYYY-MM-DD): ");
        LocalDate toDate = LocalDate.parse(new Scanner(System.in).nextLine());
        for (RepairReport report:reports)
            if (report.getRepairDate().isAfter(fromDate) || report.getRepairDate().isBefore(toDate))
                System.out.println(report);
    }

    private static void findReportByCause() {
        System.out.println("Search reports for cause: ");
        String cause = new Scanner(System.in).nextLine();
        for (RepairReport report:reports)
            if (report.getCause().contains(cause))
                System.out.println(report);
    }

    private static void displayAssetRepairs() {
        System.out.println("Enter asset's ID: ");
        int id = new Scanner(System.in).nextInt();
        if (findAssetByID(id)== null){
            System.out.println("ID doesn't exist!");
            return;
        }

        for (int i = assets.size() - 1; i >= 0; i--)
            if (assets.get(i).getID() == id)
                System.out.println(assets.get(i));
    }

    private static void displayAllUsers() {
        if (!currentUser.getIsAdmin()){
            System.out.println("You are NOT authorized!");
            return;
        }
        for (User user:users)
            System.out.println(user);
    }

    private static void findAssetByUser() {
        Scanner input = new Scanner(System.in);
        int id;
        if (!currentUser.getIsAdmin()){
            System.out.println("You are NOT authorized.");
            return;
        }
        System.out.print("Enter ID: ");
        id = input.nextInt();
        for (RepairReport report:reports){
            if (report.getEngineerID() == id){
                System.out.println(report);
            }
        }
    }

    private static void findAssetByModel() {
        String model = new Scanner(System.in).nextLine();
        for (Asset asset:assets){
            if (asset.getModel().contains(model)){
                System.out.println(asset);
            }
        }
    }

    private static void retireAsset() {
        Scanner input = new Scanner(System.in);
        Asset asset;
        System.out.print("Enter asset's ID: ");
        asset = findAssetByID(input.nextInt());
        while (asset == null){
            System.out.print("Invalid ID. Try again: ");
            asset = findAssetByID(input.nextInt());
        }
        asset.retire();
    }

    private static void changePassword() {
        try {
            currentUser.setPassword();
        }
        catch (NoSuchAlgorithmException e){
            e.getStackTrace();
        }
    }

    private static void editUser() {
        Scanner input = new Scanner(System.in);
        User user;

        System.out.print("Provide a username to edit: ");
        user = findUserByName(input.nextLine());
        while (user == null){
            System.out.println("Invalid username. Try again: ");
            user = findUserByName(input.nextLine());
        }
        user.edit();
    }

    private static void addUser() {
        Scanner input = new Scanner(System.in);
        String username;
        String password;
        String createAdmin;
        System.out.print("Username: ");
        username = input.nextLine();
        while (findUserByName(username) != null){
            System.out.print("Username already exists. Try another username: ");
            username = input.nextLine();
        }
        System.out.print("Password: ");
        password = input.nextLine();

        while (true) {
            System.out.print("Do you want to create an Admin user? (y/[n]): ");
            createAdmin = input.nextLine();
            if (createAdmin.equals("") || createAdmin.toLowerCase().equals("n")) {
                users.add(new User(username, password, false));
                break;
            }
            else if (createAdmin.toLowerCase().equals("y")) {
                users.add(new User(username, password, true));
                break;
            }
            else {
                System.out.println("Invalid input.");
            }
        }
    }

    private static void addAsset() {
        int choice;
        do {
            System.out.println("Please select the type of asset you wnt to add");
            System.out.println("1- Computer");
            System.out.println("2- Switch");
            System.out.println("3- Printer");
            System.out.println("Enter the number: ");
            choice = new Scanner(System.in).nextInt();
        }
        while (choice < 1 || choice > 3);

        switch (choice){
            case 1:
                assets.add(new Computer());
                break;
            case 2:
                assets.add(new Switch());
                break;
            case 3:
                assets.add(new Printer());
                break;
        }
    }

    private static void editAsset() {
        Asset asset;
        System.out.print("Enter the asset's ID: ");
        asset = findAssetByID(new Scanner(System.in).nextInt());
        while (asset == null){
            System.out.print("Please enter a valid ID: ");
            asset = findAssetByID(new Scanner(System.in).nextInt());
        }
        switch (asset.getClass().getSimpleName().toUpperCase()){
            case "COMPUTER":
                ((Computer)asset).edit();
                break;
            case "SWITCH":
                ((Switch)asset).edit();
                break;
            case "PRINTER":
                ((Printer)asset).edit();
                break;
        }

    }

    private static void fillReport() {
        System.out.print("Please enter asset's ID: ");
        Asset asset = findAssetByID(new Scanner(System.in).nextInt());
        while (asset == null){
            System.out.println("Invalid ID. Enter a valid ID: ");
            asset = findAssetByID(new Scanner(System.in).nextInt());
        }
        reports.add(new RepairReport(asset));
    }

    private static Asset findAssetByID(int id) {
        for (Asset asset:assets)
            if (asset.getID() == id)
                return asset;
        return null;
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
            RepairReport.saveReports(reports);
        }
        catch (Exception e){
            e.getStackTrace();
        }

    }

    private static void loadAllData() {
        try {
            users = User.loadUsers();
            assets = Asset.loadAssets();
            reports = RepairReport.loadReports();
        }
        catch (IOException e){
            e.getStackTrace();
        }
    }

    static User getCurrentUser() {
        return currentUser;
    }
}