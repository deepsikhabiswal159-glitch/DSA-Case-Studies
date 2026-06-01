class AVLNode {
    int key;
    AVLNode left, right;
    int height;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

public class AVLTree {

    static int height(AVLNode n) {
        return (n == null) ? 0 : n.height;
    }

    static int balance(AVLNode n) {
        return (n == null) ? 0 :
                height(n.left) - height(n.right);
    }

    static void updateHeight(AVLNode n) {
        n.height = 1 + Math.max(height(n.left),
                                height(n.right));
    }

    // Right Rotation
    static AVLNode rotateRight(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left Rotation
    static AVLNode rotateLeft(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    static AVLNode insert(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);

        else if (key > node.key)
            node.right = insert(node.right, key);

        else
            return node;

        updateHeight(node);

        int bf = balance(node);

        // LL
        if (bf > 1 && key < node.left.key)
            return rotateRight(node);

        // RR
        if (bf < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR
        if (bf > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (bf < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    static void inorder(AVLNode root) {

        if(root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }

    public static void main(String[] args) {

        int ids[] = {20,30,35,40,45,50,60,65,70,75,80,85,90};

        AVLNode root = null;

        for(int id : ids)
            root = insert(root, id);

        System.out.print("Inorder Traversal: ");
        inorder(root);

        System.out.println("\nAVL Height = "
                + (height(root)-1));
    }
} 
    

