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
        this.setEngineerID(Integer.parseInt(args[4]));
        this.repairDate = LocalDate.parse(args[5]);
        lastID = Integer.parseInt(args[0]);
        this.setID(lastID);
    }

    RepairReport(Asset asset) {
        this.ID = ++lastID;
        this.repairDate = LocalDate.now();
        setAssetType(AssetType.valueOf(asset.getClass().getSimpleName().toUpperCase()));
        setAssetName();
        setCause();
        setRepairMethod();
        setEngineerID(ASM.getCurrentUser().getID());
    }


    static void saveReports(ArrayList<RepairReport> reports) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase\\Reports"));
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
        }
        return reports;
    }

    public int getID() {
        return ID;
    }

    private void setID(int ID) {
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

    public String getCause() {
        return cause;
    }

    private void setCause(String cause) {
        this.cause = cause;
    }

    private void setCause() {
        System.out.print("Cause of the problem: ");
        this.cause = new Scanner(System.in).nextLine();
    }

    public String getRepairMethod() {
        return repairMethod;
    }

    private void setRepairMethod(String repairMethod) {
        this.repairMethod = repairMethod;
    }

    private void setRepairMethod() {
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

    private void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    private void setAssetName() {
        System.out.print("Asset name: ");
        this.assetName = new Scanner(System.in).nextLine();
    }

    @Override
    public String toString() {
        return String.format("%d;%s: %s;Cause: %s;Method: %s;%s",getID(), getAssetType(), getAssetName(), getCause(), getRepairMethod(), getRepairDate());
    }
}
