package auto;

import java.io.*;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 16.05.2022, понедельник
 **/
public class F {
    static int n, m, k;
    static int size = 100050;

    public static int[] associations = new int[size];
    public static boolean[] visited = new boolean[size];

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
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("isomorphism.in")));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("isomorphism.out")));

        Auto firstDKA, secondDKA;

        firstDKA = getDKA(in);
        secondDKA = getDKA(in);

        out.write(dsf(1, 1, firstDKA, secondDKA) ? "YES" : "NO");

        out.close();
        in.close();
    }

    public static boolean dsf(int u, int v, Auto a1, Auto a2) {
        visited[u] = true;

        if (a1.t[u] != a2.t[v]) return false;

        boolean result = true;
        associations[u] = v;

        for (int i = 0; i < 50; i++) {
            var t1 = a1.delta[u][i];
            var t2 = a2.delta[v][i];
            if (isDiablo(t1) && !isDiablo(t2) || !isDiablo(t1) && isDiablo(t2)) return false;
            if (visited[t1]) {
                result = result && t2 == associations[t1];
            } else result = result && dsf(t1, t2, a1, a2);
        }

        return result;
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

    private static boolean isDiablo(int x) {
        return x != 0;
    }
}
