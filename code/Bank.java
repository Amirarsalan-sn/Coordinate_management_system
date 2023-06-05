public class Bank {
    public final Point coordinates ;
    public final TwoDTree branches = new TwoDTree() ;
    public final String name ;

    public Bank(double x , double y , String name) {
        coordinates = new Point(x,y);
        this.name = name;
    }

    @Override
    public String toString(){
        return "[ Bank name : " + name + ", coordinates : " + "( " + coordinates.elements[0] + " , " + coordinates.elements[1] +
                " ) ]";
    }

    public int branchesSize(){
        return branches.getSize();
    }

    public void printBranches() {
        System.out.println(branches.toString(branches.root, new StringBuilder()));
    }
}
