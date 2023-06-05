public class TwoDTree {
    public KDNode root;
    private KDNode min ;
    private double minRadius ;
    private int MIN_DEPTH;
    private int size = 0;
    private StringBuilder availBs ;

    public boolean add(Bank bank, KDNode root, int depth) {
        if (root == null) {
            this.root = new KDNode(bank);
            size++;
            return true;
        }
        int dim = depth % 2;
        if (root.data.coordinates.elements[dim] <= bank.coordinates.elements[dim]) {
            if (root.data.coordinates.equals(bank.coordinates)) {
                System.out.println("The bank you tried to add exists !");
                return false;
            }
            if (root.right == null) {
                KDNode newNode = new KDNode(bank);
                root.right = newNode;
                newNode.parent = root;
                size++;
                return true;
            } else {
               return add(bank, root.right, depth + 1);
            }
        } else {
            if (root.left == null) {
                KDNode newNode = new KDNode(bank);
                root.left = newNode;
                newNode.parent = root;
                size++;
                return true;
            } else {
               return add(bank, root.left, depth + 1);
            }
        }
    }

    public Bank delete(Point bank, KDNode root, int depth) {
        int dim = depth % 2;
        Bank result;
        if (root == null) {
            System.out.println("The branch you want to delete does not exist !");
            return null;
        }

        if (root.data.coordinates.equals(bank)) {
            if (!(root.data instanceof Branch)) {
                System.out.println("You can't delete a head office ");
                return null;
            }
            result = root.data;
            if (root.right != null) {
                KDNode min = findMin(root.right, depth + 1, (depth) % 2, null);
                root.data = min.data;
                delete(min.data.coordinates, min, MIN_DEPTH);
            } else if (root.left != null) {
                KDNode min = findMin(root.left, depth + 1, (depth) % 2, null);
                root.data = min.data;
                delete(min.data.coordinates, min, MIN_DEPTH);
                root.right = root.left;
                root.left = null;
            } else {
                if (root.parent != null) {
                    root.parent.delChild(root);
                } else {
                    this.root = null;
                }
                root.parent = null;
                size--;
            }
        } else {
            if (root.data.coordinates.elements[dim] <= bank.elements[dim]) {
                result = delete(bank, root.right, depth + 1);
            } else {
                result = delete(bank, root.left, depth + 1);
            }
        }
        return result;
    }

    private KDNode findMin(KDNode root, int depth, int dim, KDNode min) {
        if (root == null) {
            return min;
        }
        int curDim = depth % 2;
        if (min == null) {
            min = root;
            MIN_DEPTH = depth;
        } else if (root.data.coordinates.elements[dim] < min.data.coordinates.elements[dim]) {
            min = root;
            MIN_DEPTH = depth;
        }
        if (curDim != dim) {
            min = findMin(root.right, depth + 1, dim, min);
        }
        min = findMin(root.left, depth + 1, dim, min);
        return min;
    }

    public KDNode nearestN(Point point, KDNode root, int depth) {
        if (root == null)
            return min;
        int dim = depth % 2;
        double distance = callDist(root.data.coordinates, point);
        if (depth == 0 || distance < minRadius) {
            min = root;
            minRadius = distance;
        }
        double horDistance = callDist(new Point(dim == 0 ? root.data.coordinates.elements[0] : point.elements[0],
                dim == 0 ? point.elements[1] : root.data.coordinates.elements[1]), point);

        if (root.data.coordinates.elements[dim] <= point.elements[dim]) {
            min = nearestN(point, root.right, depth + 1);
            distance = callDist(min.data.coordinates, point);
            if (horDistance < distance)
                min = nearestN(point, root.left, depth + 1);
        } else {
            min = nearestN(point, root.left, depth + 1);
            distance = callDist(min.data.coordinates, point);
            if (horDistance < distance)
                min = nearestN(point, root.right, depth + 1);
        }
        return min;
    }

    private double callDist(Point node, Point point) {
        return (point.elements[0] - node.elements[0]) * (point.elements[0] - node.elements[0]) +
                (point.elements[1] - node.elements[1]) * (point.elements[1] - node.elements[1]);
    }

    public String availBanks(Point point, KDNode root, int depth, double radius) {
        if(depth == 0) {
            availBs = new StringBuilder();
        }
        if (root == null) {
             return availBs.toString();
        }
        int dim = depth % 2;
        double distance = callDist(root.data.coordinates, point);
        if (distance <= radius || distance - radius < 0.00001) {
            //System.out.println(root.data);
            availBs.append(root.data).append("\n");
        }
        double horDistance = callDist(new Point(dim == 0 ? root.data.coordinates.elements[0] : point.elements[0],
                dim == 0 ? point.elements[1] : root.data.coordinates.elements[1]), point);

        if (root.data.coordinates.elements[dim] <= point.elements[dim]) {
            availBanks(point, root.right, depth + 1, radius);
            if (horDistance < radius)
               availBanks(point, root.left, depth + 1, radius);
        } else {
            availBanks(point, root.left, depth + 1, radius);
            if (horDistance < distance)
                availBanks(point, root.right, depth + 1, radius);
        }
        return availBs.toString();
    }

    public String availBanks(Neighborhood neighborhood, KDNode root, int depth) {
        if(depth == 0)
            availBs = new StringBuilder();
        if (root == null)
            return availBs.toString();
        boolean btx = root.data.coordinates.elements[0] > neighborhood.max.elements[0];
        boolean stx = root.data.coordinates.elements[0] < neighborhood.min.elements[0];
        boolean bty = root.data.coordinates.elements[1] > neighborhood.max.elements[1];
        boolean sty = root.data.coordinates.elements[1] < neighborhood.min.elements[1];
        boolean xInRange = !stx && !btx;
        boolean yInRange = !sty && !bty;
        if (xInRange && yInRange) {
            //System.out.println(root.data);
            availBs.append(root.data).append("\n");
            availBanks(neighborhood, root.right, depth + 1);
            availBanks(neighborhood, root.left, depth + 1);
        } else if (xInRange) {
            if (bty) {
                if (depth % 2 == 0) {
                    availBanks(neighborhood, root.right, depth + 1);
                }
                availBanks(neighborhood, root.left, depth + 1);
            } else {
                if (depth % 2 == 0) {
                    availBanks(neighborhood, root.left, depth + 1);
                }
                availBanks(neighborhood, root.right, depth + 1);
            }
        } else if (yInRange) {
            if (btx) {
                if (depth % 2 == 1) {
                    availBanks(neighborhood, root.right, depth + 1);
                }
                availBanks(neighborhood, root.left, depth + 1);
            } else {
                if (depth % 2 == 1) {
                    availBanks(neighborhood, root.left, depth + 1);
                }
                availBanks(neighborhood, root.right, depth + 1);
            }
        } else {
            if (btx && bty) {
                availBanks(neighborhood, root.left, depth + 1);
            } else if (btx) {
                if (depth % 2 == 0) {
                    availBanks(neighborhood, root.left, depth + 1);
                } else {
                    availBanks(neighborhood, root.right, depth + 1);
                }
            } else if (bty) {
                if (depth % 2 == 0) {
                    availBanks(neighborhood, root.right, depth + 1);
                } else {
                    availBanks(neighborhood, root.left, depth + 1);
                }
            } else {
                availBanks(neighborhood, root.right, depth + 1);
            }
        }
        return availBs.toString();
    }

    public int getSize() {
        return size;
    }

    public String toString(KDNode root, StringBuilder str) { // pre order // post order ...
        if (root == null) {
            return str.toString();
        }
        str.append(root.data).append("\n");
        toString(root.left, str);
        return toString(root.right, str);
    }
}
