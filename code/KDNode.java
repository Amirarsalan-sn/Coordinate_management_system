public class KDNode {
    public Bank data ;
    public KDNode parent ;
    public KDNode left ;
    public KDNode right ;

    public KDNode(Bank data) {
        this.data = data;
    }

    public void delChild(KDNode root) {
        if(this.right != null) {
            if (this.right.data.coordinates.equals(root.data.coordinates)) {
                this.right = null;
            }
        }if(this.left != null){
            if(this.left.data.coordinates.equals(root.data.coordinates)) {
                this.left = null;
            }
        }
    }
}
