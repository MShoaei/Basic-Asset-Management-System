import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Asset {
    //private static String ID;
    private static int lastID = 0;
    private int ID;
    private String model;
    private LocalDate BDate;
    private Location location;
    private State state;

    Asset() {
        this.ID = ++lastID;
        setBDate();
    }

    Asset(int id, String date) {
        this.ID = id;
        lastID = this.ID;
        setBDate(date);
    }

    static void saveAssets(ArrayList<Asset> assets) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase\\Assets"));
        for (Asset asset : assets) {
            switch (asset.getClass().getSimpleName()){
                case "Computer":
                    writer.write(((Computer)asset).toString(true));
                    break;
                case "Switch":
                    writer.write(((Switch)asset).toString(true));
                    break;
                case "Printer":
                    writer.write(((Printer)asset).toString(true));
                    break;
            }
        }
        writer.close();
    }

    static ArrayList<Asset> loadAssets() throws IOException {
        ArrayList<Asset> assets = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("DataBase\\Assets"));
        String line;
        String[] args;

        while ((line = reader.readLine()) != null){

            args = line.split(";")[1].split(":");
            switch (line.split(";")[0]) {
                case "Computer":
                    assets.add(new Computer(args));
                    break;
                case "Switch":
                    assets.add(new Switch(args));
                    break;
                case "Printer":
                    assets.add(new Printer(args));
                    break;
            }
        }
        return assets;
    }

    void retire(){
        this.setState(State.RETIRED);
    }

    void editState() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter State: ");
        input = sc.nextLine();
        if (!input.equals(""))
            setState(input);
    }

    void editLocation() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter new location (School,Owner,HoldingPlace): ");
        input = sc.nextLine();
        if (!input.equals("")){
            if (input.split("\\s*,\\s*").length == 3) {
                this.setLocation(input.split("\\s*,\\s*"));
            }
        }
    }

    public String getModel() {
        return model;
    }

    void setModel(String model) {
        this.model = model;
    }

    public Location getLocation() {
        return location;
    }

    void setLocation() {
        // TODO: if the input contains "," the program will break. PLEASE FIX!
        Scanner input = new Scanner(System.in);
        System.out.println("Enter location information: ");

        System.out.print("  school: ");
        String school = input.nextLine();

        System.out.print("  owner: ");
        String owner = input.nextLine();

        System.out.print("  holdingPlace: ");
        String holdingPlace = input.nextLine();

        setLocation(new Location(school, owner, holdingPlace));
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    void setLocation(String[] location){
        this.location = new Location(location[0],location[1],location[2]);
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    void setState(String state){
        setState(State.valueOf(state.toUpperCase()));
    }

    public LocalDate getBDate() {
        return BDate;
    }

    private void setBDate(){ this.BDate = LocalDate.now(); }

    private void setBDate(String date){
        this.BDate = LocalDate.parse(date);
    }

    void setState(AssetType type) {
        int choice;
        do {

            System.out.println("Select the current state of this "+type.toString());
            System.out.println("1- Healthy");
            System.out.println("2- Broken");
            System.out.println("3- Retired");
            System.out.print("Enter the number: ");
            choice = new Scanner(System.in).nextInt(); // is it efficient?
        }
        while (choice>3 || choice<1);

        switch (choice) {
            case 1:
                this.setState(State.HEALTHY);
                break;
            case 2:
                this.setState(State.BROKEN);
                break;
            case 3:
                this.setState(State.RETIRED);
                break;
        }
    }

    public int getID() {
        return ID;
    }
}