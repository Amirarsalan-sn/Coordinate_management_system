/**
 *                                (max)
 *                                 /
 *      *-------------------------*
 *      |                         |
 *      |                         |
 *      |                         |
 *      *-------------------------*
 *     /
 *  (min)
 *
 *
 *
 *  OR :
 *
 *                 (max)
 *                  /
 *        *--------*
 *        |        |
 *        |        |
 *        |        |
 *        |        |
 *        |        |
 *        |        |
 *        *--------*
 *       /
 *    (min)
 *
 */

public class Neighborhood {
    public final String name ;
    public final Point min;
    public final Point max;


    public Neighborhood(String name,double minX , double minY , double maxX , double maxY) {
        this.name = name;
        min = new Point(minX , minY);
        max = new Point(maxX , maxY);
    }
}
