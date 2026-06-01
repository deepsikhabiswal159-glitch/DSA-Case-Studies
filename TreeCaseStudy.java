import java.util.*;

// ======================= B-TREE ==========================
class BTreeNode {
    int t;
    int n;
    int keys[];
    BTreeNode children[];
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        keys = new int[2 * t - 1];
        children = new BTreeNode[2 * t];
        n = 0;
    }

    void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf)
                children[i].traverse();
            System.out.print(keys[i] + " ");
        }
        if (!leaf)
            children[i].traverse();
    }

    BTreeNode search(int k) {
        int i = 0;
        while (i < n && k > keys[i])
            i++;

        if (i < n && keys[i] == k)
            return this;

        if (leaf)
            return null;

        return children[i].search(k);
    }

    void insertNonFull(int k) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }

            keys[i + 1] = k;
            n++;
        } else {
            while (i >= 0 && keys[i] > k)
                i--;

            if (children[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);

                if (keys[i + 1] < k)
                    i++;
            }

            children[i + 1].insertNonFull(k);
        }
    }

    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = y.keys[j + t];

        if (!y.leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--)
            children[j + 1] = children[j];

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--)
            keys[j + 1] = keys[j];

        keys[i] = y.keys[t - 1];
        n++;
    }
}

class BTree {
    BTreeNode root;
    int t;

    BTree(int t) {
        this.t = t;
        root = null;
    }

    void traverse() {
        if (root != null)
            root.traverse();
    }

    BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    void insert(int k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);

                s.children[0] = root;
                s.splitChild(0, root);

                int i = 0;
                if (s.keys[0] < k)
                    i++;

                s.children[i].insertNonFull(k);
                root = s;
            } else {
                root.insertNonFull(k);
            }
        }
    }
}

// ======================= SEGMENT TREE ==========================
class SegmentTree {
    int[] tree;
    int n;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 1, 0, n - 1);
    }

    void build(int[] arr, int node, int start, int end) {
        if (start == end)
            tree[node] = arr[start];
        else {
            int mid = (start + end) / 2;

            build(arr, 2 * node, start, mid);
            build(arr, 2 * node + 1, mid + 1, end);

            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }
    }

    int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l)
            return 0;

        if (l <= start && end <= r)
            return tree[node];

        int mid = (start + end) / 2;

        return query(2 * node, start, mid, l, r)
                + query(2 * node + 1, mid + 1, end, l, r);
    }

    int query(int l, int r) {
        return query(1, 0, n - 1, l, r);
    }
}

// ======================= FENWICK TREE ==========================
class FenwickTree {
    int[] bit;
    int n;

    FenwickTree(int n) {
        this.n = n;
        bit = new int[n + 1];
    }

    void update(int idx, int val) {
        while (idx <= n) {
            bit[idx] += val;
            idx += idx & (-idx);
        }
    }

    int sum(int idx) {
        int result = 0;

        while (idx > 0) {
            result += bit[idx];
            idx -= idx & (-idx);
        }

        return result;
    }

    int rangeSum(int l, int r) {
        return sum(r) - sum(l - 1);
    }
}

// ======================= B+ TREE (SIMPLIFIED) ==========================
class BPlusTree {
    TreeMap<Integer, String> map = new TreeMap<>();

    void insert(int key, String value) {
        map.put(key, value);
    }

    void rangeSearch(int start, int end) {
        System.out.println("Range Scan:");
        for (Map.Entry<Integer, String> entry :
                map.subMap(start, true, end, true).entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// ======================= MAIN ==========================
public class TreeCaseStudy {

    public static void main(String[] args) {

        System.out.println("===== B-TREE =====");
        BTree btree = new BTree(3);

        int[] values = {10,20,5,6,12,30,7,17};

        for(int v : values)
            btree.insert(v);

        System.out.print("Traversal: ");
        btree.traverse();

        System.out.println("\nSearch 12: "
                + (btree.search(12) != null ? "Found" : "Not Found"));

        System.out.println("\n===== B+ TREE =====");
        BPlusTree bpt = new BPlusTree();

        bpt.insert(101, "Account A");
        bpt.insert(102, "Account B");
        bpt.insert(103, "Account C");
        bpt.insert(104, "Account D");

        bpt.rangeSearch(102, 104);

        System.out.println("\n===== SEGMENT TREE =====");
        int[] arr = {5,8,6,3,2,7,2,6};

        SegmentTree st = new SegmentTree(arr);

        System.out.println(
                "Range Sum (2 to 6): "
                        + st.query(2,6));

        System.out.println("\n===== FENWICK TREE =====");

        FenwickTree ft = new FenwickTree(arr.length);

        for(int i=0;i<arr.length;i++)
            ft.update(i+1, arr[i]);

        System.out.println(
                "Prefix Sum (1 to 5): "
                        + ft.sum(5));

        System.out.println(
                "Range Sum (3 to 7): "
                        + ft.rangeSum(3,7));
    }
}