import java.util.*;

public class PowerGridMST {

    static final int V = 8;

    static int minKey(int key[], boolean mstSet[]) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    static void primMST(int graph[][]) {

        int parent[] = new int[V];
        int key[] = new int[V];
        boolean mstSet[] = new boolean[V];

        Arrays.fill(key, Integer.MAX_VALUE);

        key[0] = 0;
        parent[0] = -1;

        for (int count = 0; count < V - 1; count++) {

            int u = minKey(key, mstSet);
            mstSet[u] = true;

            for (int v = 0; v < V; v++) {

                if (graph[u][v] != 0 &&
                    !mstSet[v] &&
                    graph[u][v] < key[v]) {

                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        String[] nodes = {"M","A","B","C","D","E","F","G"};

        int totalCost = 0;

        System.out.println("MST Edges:");

        for (int i = 1; i < V; i++) {
            System.out.println(
                nodes[parent[i]] + " - " +
                nodes[i] + " : " +
                graph[i][parent[i]]
            );

            totalCost += graph[i][parent[i]];
        }

        System.out.println("\nTotal MST Cost = " +
                           totalCost + " Crore");
    }

    public static void main(String[] args) {

        int graph[][] = {

        //M A B C D E F G

        {0,4,3,0,0,0,0,0}, // M
        {4,0,2,5,0,0,0,0}, // A
        {3,2,0,3,4,0,0,0}, // B
        {0,5,3,0,2,6,0,0}, // C
        {0,0,4,2,0,3,2,0}, // D
        {0,0,0,6,3,0,0,7}, // E
        {0,0,0,0,2,0,0,4}, // F
        {0,0,0,0,0,7,4,0}  // G

        };

        primMST(graph);
    }
}