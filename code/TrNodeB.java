/**
 *  children = [0 , ..... , 9 , a , ..... , z]
 *             /           /   /           /
 *         index(0) index(9) index(10)  index(35)
 */

public class TrNodeB {
    public Bank data ;
    public TrNodeB parent ;
    public TrNodeB[] children;

    public TrNodeB(Bank data) {
        this.data = data;
    }
}
