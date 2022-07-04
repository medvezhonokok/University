package auto;

import java.io.*;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 16.05.2022, понедельник
 **/
public class G {
    static int n, m, k;

    static int size = 1050;
    static boolean[][] visited = new boolean[size][size];

    private static class Auto {
        int[][] delta;
        int[] t;

        public Auto(int n) {
            t = new int[n + 2];
            delta = new int[n + 2][50];
        }
    }

    private static void getInput(String[] params) {
        n = Integer.parseInt(params[0]);
        m = Integer.parseInt(params[1]);
        k = Integer.parseInt(params[2]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("equivalence.in")));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("equivalence.out")));

        Auto firstDKA, secondDKA;

        firstDKA = getDKA(in);
        secondDKA = getDKA(in);

        out.write(eq(1, 1, firstDKA, secondDKA) ? "YES" : "NO");

        out.close();
        in.close();
    }

    public static boolean eq(int u, int v, Auto a1, Auto a2) {
        visited[u][v] = true;

        if (a1.t[u] != a2.t[v]) return false;

        for (int i = 0; i < 50; i++) {
            int t1 = a1.delta[u][i];
            int t2 = a2.delta[v][i];
            if (visited[t1][t2] || eq(t1, t2, a1, a2)) {
                continue;
            }
            return false;
        }

        return true;
    }

    private static Auto getDKA(BufferedReader in) throws IOException {
        Auto auto;
        String[] params;
        params = in.readLine().split(" ");
        getInput(params);
        auto = new Auto(n);
        params = in.readLine().split(" ");
        for (int i = 0; i < k; i++) {
            auto.t[Integer.parseInt(params[i])] = 1;
        }
        for (int i = 0; i < m; i++) {
            params = in.readLine().split(" ");
            auto.delta[Integer.parseInt(params[0])][params[2].charAt(0) - 'a'] = Integer.parseInt(params[1]);
        }

        return auto;
    }
}
