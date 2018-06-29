import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

    Asset(int ID, String date){
        lastID++;
        this.ID = ID;
        setBDate(date);
    }

    static void saveAssets(ArrayList<Asset> assets) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase\\AssetsWrite"));
        for (Asset asset : assets) {
            switch (asset.getClass().getSimpleName()){
                case "Computer":
                    writer.write(((Computer)asset).toString(true));
                    break;
                case "Printer":
//                    writer.write(((Printer)asset).toString(true));
                    break;
                case "Switch":
//                    writer.write(((Switch)asset).toString(true));
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
                case "Printer":
                    assets.add(new Printer());
                    break;
                case "Switch":
                    assets.add(new Switch());
                    break;
            }
        }
        return assets;
    }

//    protected abstract void retire();

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    protected abstract void setLocation(String[] location);

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getBDate() {
        return BDate;
    }

    private void setBDate(){ this.BDate = LocalDate.now(); }

    private void setBDate(String date){
        this.BDate = LocalDate.parse(date);
    }

    private void setState() {
    }

    public int getID() {
        return ID;
    }
}



class Location {
    private String school;
    private String owner;
    private String holdingPlace;

    Location(String school, String owner, String holdingPlace) {
        this.setSchool(school);
        this.setOwner(owner);
        this.setHoldingPlace(holdingPlace);
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHoldingPlace() {
        return holdingPlace;
    }

    public void setHoldingPlace(String holdingPlace) {
        this.holdingPlace = holdingPlace;
    }
}