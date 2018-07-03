import java.util.Scanner;

public class Switch extends Asset{
    private int ports;
    private boolean manageable;
    Switch(){
        super();
        createSwitch();
    }

    Switch(String[] args) {
        super(Integer.parseInt(args[0]),args[6]);
        setModel(args[1]);
        setPorts(Integer.parseInt(args[2]));
        setManageable(Boolean.parseBoolean(args[3]));
        setState(args[4]);
        setLocation(args[5].split(","));
    }

    private void createSwitch() {
        System.out.println("Please provide the required information:");
        setModel();
        setPorts();
        setManageable();
        setState(AssetType.SWITCH);
        setLocation();
    }

    void edit() {
        System.out.println("Edit mode. empty string leaves the field UNCHANGED.");
        System.out.println(this);
        editPorts();
        editManageable();
        editState();
        editLocation();
    }

    private void editManageable() {
        Scanner sc = new Scanner(System.in);
        boolean input;
        System.out.print("Enter GPU: ");
        input = sc.nextBoolean();
        setManageable(input);
    }

    private void editPorts() {
        Scanner sc = new Scanner(System.in);
        int count;
        System.out.print("Enter ports count: ");
        count = sc.nextInt();
        if (!(count < 0))
            setPorts(count);
    }

    private void setModel(){
        System.out.print("Model: ");
        String model = new Scanner(System.in).nextLine();
//        if model valid
        super.setModel(model);
    }

    public int getPorts() {
        return ports;
    }

    private void setPorts(){
        System.out.print("Ports: ");
        this.ports = new Scanner(System.in).nextInt();
    }

    private void setPorts(int ports) {
        this.ports = ports;
    }

    private boolean isManageable() {
        return manageable;
    }

    private void setManageable(){
        System.out.print("Manageable (true/false): ");
        this.manageable = new Scanner(System.in).nextBoolean();
    }

    private void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    String toString(boolean isSaving){
        if(isSaving){
            Location temp = getLocation();
            return String.format("Switch;%d:%s:%d:%s:%s:%s,%s,%s:%s\n",
                    getID(), getModel(), getPorts(), isManageable(), getState(),
                    temp.getSchool(), temp.getOwner(), temp.getHoldingPlace(), getBDate());
        }
        return toString();
    }

    @Override
    public String toString(){
        return String.format("ID: %d Switch; Model: %-10.9s, Ports: %-5d, Manageable: %-5s, State: %-10.9s, Location: %-11.10s, %-11.10s, %-11.10s, BDate: %-10s",
                getID(),getModel(), getPorts(), isManageable(), getState(),
                getLocation().getSchool(), getLocation().getOwner(),
                getLocation().getHoldingPlace(),
                getBDate());
    }
}
