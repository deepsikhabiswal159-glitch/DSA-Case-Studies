class BSTNode {
    int key;
    BSTNode left, right;
    BSTNode(int key) {
        this.key = key;
    }}
public class PlainBST {
    static BSTNode insert(BSTNode root, int key) {
        if (root == null)
            return new BSTNode(key);
        if (key < root.key)
            root.left = insert(root.left, key);
        else
            root.right = insert(root.right, key);
        return root;
    }
    static int height(BSTNode root) {
        if (root == null)
            return -1;
        return 1 + Math.max(height(root.left),
                            height(root.right));
    }
    static void inorder(BSTNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }}
    public static void main(String[] args) {
        int ids[] = {20,30,35,40,45,50,60,65,70,75,80,85,90};
        BSTNode root = null;
        for(int id : ids)
            root = insert(root, id);
        System.out.print("Inorder Traversal: ");
        inorder(root);
        System.out.println("\nHeight = " + height(root));
    }}