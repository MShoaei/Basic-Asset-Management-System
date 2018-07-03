import java.util.Scanner;

public class Printer extends Asset {
    private boolean color;

    Printer() {
        super();
        createPrinter();
    }

    Printer(String[] args) {
        super(Integer.parseInt(args[0]),args[5]);
        setModel(args[1]);
        setColor(Boolean.parseBoolean(args[2]));
        setState(args[3]);
        setLocation(args[4].split(","));
    }

    private void createPrinter() {
        setColor();
    }

    void edit() {
        System.out.println("Edit mode. empty string leaves the field UNCHANGED.");
        System.out.println(this);
        editColor();
        editState();
        editLocation();
    }

    public boolean isColor() {
        return color;
    }

    private void setColor() {
        System.out.println("Color: ");
        this.color = new Scanner(System.in).nextBoolean();
    }

    private void setColor(boolean color) {
        this.color = color;
    }

    private void editColor() {
        Scanner sc = new Scanner(System.in);
        boolean input;
        System.out.print("Edit Color: ");
        input = sc.nextBoolean();
        setColor(input);
    }

    String toString(boolean isSaving){
        if(isSaving){
            Location temp = getLocation();
            return String.format("Switch;%d:%s:%b:%s:%s,%s,%s:%s\n",
                    getID(), getModel(), isColor(), getState(),
                    temp.getSchool(), temp.getOwner(), temp.getHoldingPlace(), getBDate());
        }
        return toString();
    }

    @Override
    public String toString(){
        return String.format("ID: %d Printer; Model: %-10.9s, Color: %-5s, State: %-10.9s, Location: %-11.10s, %-11.10s, %-11.10s, BDate: %-10s",
                getID(),getModel(), getState(), isColor(),
                getLocation().getSchool(),
                getLocation().getOwner(),
                getLocation().getHoldingPlace(),
                getBDate());
    }
}
