package auto;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 23.06.2022, четверг
 **/

import java.io.*;
import java.util.*;

public class E {
    static int n, m, k, l;
    static int mod = 1000000007;

    private static class Auto {
        Map<Integer, Integer> cur;
        Map<Integer, Integer> buff;

        int[][] delta;
        int[] t;

        public Auto() {
            t = new int[130];
            delta = new int[130][50];
            cur = new HashMap<>();
            cur.put(1, 1);
            buff = new HashMap<>();
        }

        public long count() {
            long answer = 0;
            for (Iterator<Integer> iterator = this.cur.keySet().iterator(); iterator.hasNext(); ) {
                Integer it = iterator.next();
                if (this.t[it] == 1) {
                    answer += this.cur.get(it);
                }
            }

            return answer;
        }

        private void sueta() {
            this.cur = new HashMap<>();
            cur.put(1, 1);
            int i = 0;
            while (i < l) {
                this.buff = new HashMap<>();

                for (Iterator<Integer> iterator = this.cur.keySet().iterator(); iterator.hasNext(); ) {
                    Integer it = iterator.next();
                    int j = 0;
                    while (j < 50) {
                        int key = this.delta[it][j];
                        if (key > 0) {
                            this.buff.put(key, this.buff.containsKey(key) ?
                                    (this.buff.get(key) + this.cur.get(it)) % (mod) :
                                    (this.cur.get(it)) % (mod));
                        }
                        j++;
                    }
                }
                this.cur = this.buff;
                i++;
            }
        }
    }

    private static class NFA extends Auto {
        int[][][] delta_NFA;

        public NFA(int n) {
            super();
            delta_NFA = new int[130][50][130];
        }
    }

    private static void inizializeNFA(BufferedReader in, NFA nfa, Auto auto) throws IOException {
        String[] params;
        params = in.readLine().split(" ");
        for (int i = 0; i < k; i++) nfa.t[Integer.parseInt(params[i])] = 1;

        int i = 0;
        while (i < m) {
            params = in.readLine().split(" ");
            nfa.delta_NFA[Integer.parseInt(params[0])][params[2].charAt(0) - 'a'][Integer.parseInt(params[1])] = 1;
            i++;
        }

        auto.t[1] = nfa.t[1];
    }

    private static void getInput(String[] params) {
        n = Integer.parseInt(params[0]);
        m = Integer.parseInt(params[1]);
        k = Integer.parseInt(params[2]);
        l = Integer.parseInt(params[3]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("problem5.in")));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("problem5.out")));

        String[] params;
        params = in.readLine().split(" ");

        getInput(params);

        Auto auto = new Auto();
        NFA nfa = new NFA(n);

        inizializeNFA(in, nfa, auto);

        solve(auto, nfa);
        auto.sueta();

        out.write(String.valueOf(auto.count() % mod));

        out.close();
        in.close();
    }

    private static void solve(Auto auto, NFA nfa) {
        var p = new ArrayList<Set<Integer>>();
        var q = new Stack<Set<Integer>>();

        p.add(Collections.singleton(1));
        q.add(Collections.singleton(1));

        while (true) {
            if (q.size() == 0) break;
            Set<Integer> pp = q.pop();
            int ss = p.indexOf(pp) + 1;

            for (int c = 0; c < 50; c++) {
                var temp = new LinkedHashSet<Integer>();
                int ind1 = -1, ind;

                for (int z : pp)
                    for (int i = 0; i < 130; i++)
                        if (nfa.delta_NFA[z][c][i] == 1) {
                            temp.add(i);
                            if (nfa.t[i] == 1) ind1 = i;
                        }

                if (temp.size() != 0) {
                    if (p.contains(temp)) {
                        ind = p.indexOf(temp) + 1;
                    } else {
                        ind = p.size() + 1;
                        q.add(temp);
                        p.add(temp);
                        if (ind1 != -1) {
                            auto.t[ind] = nfa.t[ind1];
                        } else auto.t[ind] = 0;
                    }
                    auto.delta[ss][c] = ind;
                }
            }
        }
    }
}