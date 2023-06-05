public class Point {
    /**
     * first element -> x , second element -> y .
     */

    public final double[] elements;

    public Point(double x , double y) {
        elements = new double[]{x , y};
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Point) {
            Point temp = ((Point) o);
            return temp.elements[0] == this.elements[0] && temp.elements[1] == this.elements[1];
        }
        return false;
    }
}
