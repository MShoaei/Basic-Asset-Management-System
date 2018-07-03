import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

class RepairReport {

    private static int lastID = 0;
    private int ID;
    private int engineerID;
    private LocalDate repairDate;
    private AssetType type;
    private String cause;
    private String repairMethod;
    private String assetName;


    private RepairReport (String[] args){
        this.setAssetType(AssetType.valueOf(args[1].split(":")[0].toUpperCase()));
        this.setAssetName(args[1].split(":")[1]);
        this.setCause(args[2].split(":")[1]);
        this.setRepairMethod(args[3].split(":")[1]);
        this.repairDate = LocalDate.parse(args[4]);
        this.setEngineerID(Integer.parseInt(args[5]));
        lastID = Integer.parseInt(args[0]);
        this.setID(lastID);
    }

    RepairReport(Asset asset) {
        this.repairDate = LocalDate.now();
        setAssetType(AssetType.valueOf(asset.getClass().getSimpleName().toUpperCase()));
        setAssetName();
        setCause();
        setRepairMethod();
        setEngineerID(ASM.getCurrentUser().getID());
    }


    static void saveReports(ArrayList<RepairReport> reports) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase\\ReportsWrite"));
        for (RepairReport report:reports){
            writer.write(report.toString());
        }
        writer.close();
    }

    static ArrayList<RepairReport> loadReports () throws IOException {
        ArrayList<RepairReport> reports = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("DataBase\\Reports"));
        String line;
        while ((line = reader.readLine()) != null){
            reports.add(new RepairReport(line.split(";")));
            /*reports.add(new RepairReport(Integer.parseInt(line.split(";")[0]),
                                        Integer.parseInt(line.split(";")[5]),
                                        AssetType.valueOf(line.split(";")[1].split(":")[0].toUpperCase()),
                                        line.split(";")[2],
                                        line.split(";")[3],
                                        line.split(";")[4]));*/
        }
        return reports;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getRepairDate() {
        return repairDate;
    }

    public AssetType getAssetType() {
        return type;
    }

    private void setAssetType(AssetType assetType) {
        this.type = assetType;
    }

    /*public void setAssetType() {
        int choice;
        do {
            System.out.println("Select the type of asset");
            System.out.println("1- Computer");
            System.out.println("2- Switch");
            System.out.println("3- Printer");
            System.out.print("Enter the number: ");
            choice = new Scanner(System.in).nextInt(); // is it efficient?
        }
        while (choice>3 || choice<1);

        switch (choice) {
            case 1:
                setAssetType(AssetType.COMPUTER);
                break;
            case 2:
                setAssetType(AssetType.SWITCH);
                break;
            case 3:
                setAssetType(AssetType.PRINTER);
                break;
        }
    }*/

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setCause() {
        System.out.print("Cause of the problem: ");
        this.cause = new Scanner(System.in).nextLine();
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    public void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    public void setRepairMethod() {
        System.out.print("The repair method: ");
        this.repairMethod = new Scanner(System.in).nextLine();
    }

    public int getEngineerID() {
        return engineerID;
    }

    private void setEngineerID(int engineerID) {
        this.engineerID = engineerID;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setAssetName() {
        System.out.print("Asset name: ");
        this.assetName = new Scanner(System.in).nextLine();
    }

    @Override
    public String toString() {
        return String.format("%d;%s: %s;Cause: %s; Method: %s;%s",getID(), getAssetType(), getAssetName(), getCause(), getRepairMethod(), getRepairDate());
    }
}
