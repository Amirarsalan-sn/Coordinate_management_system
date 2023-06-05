public class Branch extends Bank {
    public final String branchName ;

    public Branch(double x, double y, String name ,String branchName) {
        super(x, y, name);
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "[ Branch name : " + branchName + " , Bank name : " + name + " , coordinates : " + "( " + coordinates.elements[0]
                + " , " + coordinates.elements[1] + " ) ]";
    }
}
