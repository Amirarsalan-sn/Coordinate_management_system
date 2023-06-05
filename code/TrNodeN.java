/**
 *  children = [0 , ..... , 9 , a , ..... , z]
 *             /           /   /           /
 *         index(0) index(9) index(10)  index(35)
 */

public class TrNodeN {
    public Neighborhood data ;
    public TrNodeN parent ;
    public TrNodeN[] children;

    public TrNodeN(Neighborhood data) {
        this.data = data;
    }
}
