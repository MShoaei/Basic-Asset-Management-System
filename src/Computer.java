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
        setState();
        setLocation();
        // BDate


    }

    //protected void retire(){}

    private void setModel(){

        System.out.print("Model: ");
        String model = new Scanner(System.in).nextLine();
//        if model valid
        super.setModel(model);
    }

    private void setLocation(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter location information: ");

        System.out.print("  school: ");
        String school = input.nextLine();

        System.out.print("  owner: ");
        String owner = input.nextLine();

        System.out.print("  holdingPlace: ");
        String holdingPlace = input.nextLine();

        super.setLocation(new Location(school, owner, holdingPlace));
    }

    @Override
    protected void setLocation(String[] location){
        setLocation(new Location(location[0], location[1], location[2]));
    }

    private void setState(){
        int choice;
        do {

            System.out.println("Select the current state of this computer: ");
            System.out.println("1- Healthy");
            System.out.println("2- Broken");
            System.out.println("3- Retired");
            System.out.print("Enter the number: ");
            choice = new Scanner(System.in).nextInt(); // is it efficient?
        }
        while (choice>3 || choice<1);

        switch (choice) {
            case 1:
                super.setState(State.HEALTHY);
                break;
            case 2:
                super.setState(State.BROKEN);
                break;
            case 3:
                super.setState(State.RETIRED);
                break;
        }

    }

    private void setState(String state){
        super.setState(State.valueOf(state.toUpperCase()));
    }

    private boolean validInput(String s){
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

    public String toString(boolean isSaving){
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
        return String.format("Computer; Model: %s, CPU: %s, RAM: %s, Hard: %s, GPU: %s, State: %s, Location: %s, BDate: %s",
                getModel(), getCPU(), getRAM(), getHard(), getGPU(), getState(), getLocation(), getBDate());
    }
}