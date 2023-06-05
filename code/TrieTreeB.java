/**
 *  children = [0 , ..... , 9 , a , ..... , z]
 *             /           /   /           /
 *         index(0) index(9) index(10)  index(35)
 */

public class TrieTreeB {
    public TrNodeB root = new TrNodeB(null);

    public boolean add(TrNodeB root, Bank bank, int depth) {
        if (root.children == null) {
            root.children = new TrNodeB[36];
        }
        String name = bank.name;
        char ch = name.charAt(depth);
        TrNodeB nodeB = null;
        if ('0' <= ch && ch <= '9') {
            nodeB = root.children[ch - 48];
            if (nodeB == null) {
                nodeB = new TrNodeB(null);
                root.children[ch - 48] = nodeB;
                nodeB.parent = root;
            }
        } else if ('a' <= ch && ch <= 'z') {
            nodeB = root.children[ch - 87];
            if (nodeB == null) {
                nodeB = new TrNodeB(null);
                root.children[ch - 87] = nodeB;
                nodeB.parent = root;
            }
        }
        if (depth == (name.length() - 1)) {
            if (nodeB.data != null) {
                System.out.println("a Bank with name : " + name + " exists !");
                return false;
            }
            nodeB.data = bank;
            return true;
        } else {
            return add(nodeB, bank, depth + 1);
        }
    }

    public Bank find(TrNodeB root, String name, int depth) {
        if (root == null || root.children == null) {
            return null;
        }
        char ch = name.charAt(depth);
        TrNodeB nodeB = null;
        if ('0' <= ch && ch <= '9') {
            nodeB = root.children[ch - 48];
            if (nodeB == null) {
                return null;
            }
        } else if ('a' <= ch && ch <= 'z') {
            nodeB = root.children[ch - 87];
            if (nodeB == null) {
                return null;
            }
        }
        if (depth == (name.length() - 1)) {
            return nodeB.data;
        }
        return find(nodeB, name, depth + 1);
    }

    public void remove(TrNodeB root ,Bank bank , int depth) {
        if (root == null || root.children == null) {
            return;
        }
        String name = bank.name;
        char ch = name.charAt(depth);
        TrNodeB nodeB = null;
        int index = 0 ;
        if ('0' <= ch && ch <= '9') {
            index = ch - 48;
            nodeB = root.children[index];
            if (nodeB == null) {
                return;
            }
        } else if ('a' <= ch && ch <= 'z') {
            index = ch - 87;
            nodeB = root.children[index];
            if (nodeB == null) {
                return;
            }
        }
        if (depth == (name.length() - 1)) {
            nodeB.data = null;
            if(nodeB.children == null) {
                root.children[index] = null;
                nodeB.parent = null;
            }
        }
        remove(nodeB, bank, depth + 1);
    }
}
