public class TrieTreeN {
    public TrNodeN root = new TrNodeN(null);

    public boolean add(TrNodeN root, Neighborhood neighborhood, int depth) {
        if (root.children == null) {
            root.children = new TrNodeN[36];
        }
        String name = neighborhood.name;
        char ch = name.charAt(depth);
        TrNodeN nodeB = null;
        if ('0' <= ch && ch <= '9') {
            nodeB = root.children[ch - 48];
            if (nodeB == null) {
                nodeB = new TrNodeN(null);
                root.children[ch - 48] = nodeB;
                nodeB.parent = root;
            }
        } else if ('a' <= ch && ch <= 'z') {
            nodeB = root.children[ch - 87];
            if (nodeB == null) {
                nodeB = new TrNodeN(null);
                root.children[ch - 87] = nodeB;
                nodeB.parent = root;
            }
        }
        if (depth == (name.length() - 1)) {
            if (nodeB.data != null) {
                System.out.println("a Neighborhood with name : " + name + " exists !");
                return false;
            }
            nodeB.data = neighborhood;
            return true;
        } else {
            return add(nodeB, neighborhood, depth + 1);
        }
    }

    public Neighborhood find(TrNodeN root, String name, int depth) {
        if (root == null || root.children == null) {
            return null;
        }
        char ch = name.charAt(depth);
        TrNodeN nodeB = null;
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

    public void remove(TrNodeN root ,Neighborhood neighborhood , int depth) {
        if (root == null || root.children == null) {
            return;
        }
        String name = neighborhood.name;
        char ch = name.charAt(depth);
        TrNodeN nodeB = null;
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
        remove(nodeB, neighborhood, depth + 1);
    }
}
