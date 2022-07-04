package auto;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 15.05.2022, воксресенье
 **/

public class A {
    static int mod = 1000000007;
    static int n, m, k, len;

    private static class Auto {
        Map<Integer, Integer> cur;
        Map<Integer, Integer> buff;

        int[][] delta;
        int[] t;

        public Auto(int n) {
            t = new int[n + 2];
            delta = new int[n + 2][160];
            cur = new HashMap<>();
            cur.put(1, 1);
            buff = new HashMap<>();
        }

        private long countOfWords() {
            long ans = 0;
            for (Iterator<Integer> iterator = this.cur.keySet().iterator(); iterator.hasNext(); ) {
                Integer temp = iterator.next();
                if (this.t[temp] == 1) {
                    ans += this.cur.get(temp);
                }
            }
            return ans;
        }

        private void sueta() {
            int i = 0;
            while (i < len) {
                this.buff = new HashMap<>();
                for (Iterator<Integer> iterator = this.cur.keySet().iterator(); iterator.hasNext(); ) {
                    Integer temp = iterator.next();
                    int j = 0;
                    while (j < 160) {
                        int key = this.delta[temp][j];
                        if (key != 0) {
                            if (this.buff.containsKey(key)) {
                                this.buff.put(key, (this.buff.get(key) + this.cur.get(temp)) % (mod));
                            } else this.buff.put(key, (this.cur.get(temp)) % (mod));
                        }
                        j++;
                    }
                }
                this.cur = this.buff;
                i++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem1.in")));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem1.out")));

        String word = in.readLine();

        String[] params = in.readLine().split(" ");
        getInput(params);
        Auto auto = new Auto(n);

        params = in.readLine().split(" ");

        for (int i = 0; i < k; i++) {
            auto.t[Integer.parseInt(params[i])] = 1;
        }

        for (int i = 0; i < m; i++) {
            params = in.readLine().split(" ");
            auto.delta[Integer.parseInt(params[0])][params[2].charAt(0) - '0'] = Integer.parseInt(params[1]);
        }

        int cur = 1;

        for (int i = 0; i < word.length(); i++) {
            char symb = word.charAt(i);
            cur = auto.delta[cur][symb - '0'];
            if (cur == 0)
                break;
        }

        out.write(auto.t[cur] == 1 ? "Accepts" : "Rejects");

        out.close();
        in.close();
    }

    private static void getInput(String[] params) {
        n = Integer.parseInt(params[0]);
        m = Integer.parseInt(params[1]);
        k = Integer.parseInt(params[2]);
    }
}
