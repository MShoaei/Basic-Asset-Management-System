import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Computer extends Asset {
    private String CPU;
    private String RAM;
    private String Hard;
    private String GPU;

    Computer(){
        super();
        createComputer();
    }

    Computer(String[] args){
        super(Integer.parseInt(args[0]), args[8]);
        setModel(args[1]);
        setCPU(args[2]);
        setRAM(args[3]);
        setHard(args[4]);
        setGPU(args[5]);
        setState(args[6]);
        setLocation(args[7].split(","));
    }

    private void createComputer(){
        System.out.println("Please provide the required information:");
        setModel();
        setCPU();
        setRAM();
        setHard();
        setGPU();
        setState(AssetType.COMPUTER);
        setLocation();
        // BDate


    }

    void edit() {
        System.out.println("Edit mode. empty string leaves the field UNCHANGED.");
        System.out.println(this);
        editCPU();
        editRAM();
        editHard();
        editGPU();
        editState();
        editLocation();
    }

    private void editGPU() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter GPU: ");
        input = sc.nextLine();
        if (!input.equals(""))
            setGPU(input);
    }

    private void editHard() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter Hard: ");
        input = sc.nextLine();
        if (!input.equals(""))
            setHard(input);
    }

    private void editRAM() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter RAM: ");
        input = sc.nextLine();
        if (!input.equals(""))
            setRAM(input);
    }

    private void editCPU() {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.print("Enter CPU: ");
        input = sc.nextLine();
        if (!input.equals(""))
            setCPU(input);
    }

    private void setModel() {
        System.out.print("Model: ");
        String model = new Scanner(System.in).nextLine();
//        if model valid
        setModel(model);
    }


    private boolean validInput(String s){
        // TODO: Should be implemented for input validation like empty strings etc..
        return true;
    }

    public String getCPU() {
        return CPU;
    }

    private void setCPU() {
        System.out.print("CPU: ");
        this.CPU = new Scanner(System.in).nextLine();
    }

    private void setCPU(String cpu){
        this.CPU = cpu;
    }

    public String getRAM() {
        return RAM;
    }

    private void setRAM() {
        System.out.print("RAM: ");
        this.RAM = new Scanner(System.in).nextLine();
    }

    private void setRAM(String ram) {
        if (ram.equals("")) return;

        this.RAM = ram;
    }

    public String getHard() {
        return Hard;
    }

    private void setHard() {
        System.out.print("Hard: ");
        Hard = new Scanner(System.in).nextLine();
    }

    private void setHard(String hard){
        this.Hard = hard;
    }

    public String getGPU() {
        return GPU;
    }

    private void setGPU() {
        System.out.print("GPU: ");
        this.GPU = new Scanner(System.in).nextLine();
    }

    private void setGPU(String gpu) {
        this.GPU = gpu;
    }

    String toString(boolean isSaving){
        if(isSaving){
            Location temp = getLocation();
            return String.format("Computer;%d:%s:%s:%s:%s:%s:%s:%s,%s,%s:%s\n",
                   getID(), getModel(), getCPU(), getRAM(), getHard(), getGPU(), getState(),
                    temp.getSchool(), temp.getOwner(), temp.getHoldingPlace(), getBDate());
        }
        return toString();
    }

    @Override
    public String toString(){
        return String.format("ID: %d Computer; Model: %-10.9s, CPU: %-10.8s, RAM: %-8.6s, Hard: %-8.8s, GPU: %-6.6s, State: %-10.9s, Location: %-11.10s, %-11.10s, %-11.10s, BDate: %-10s",
                getID(),getModel(), getCPU(), getRAM(), getHard(), getGPU(), getState(),
                getLocation().getSchool(),getLocation().getOwner(),getLocation().getHoldingPlace(), getBDate());
    }
}
