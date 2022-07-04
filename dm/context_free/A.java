package context_free;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 12.06.2022, воскресенье
 **/
public class A {
    public static void main(String[] args) throws IOException {
        List<List<Pair>> list = new ArrayList<>();
        for (int i1 = 0; i1 < 1000; i1++) {
            List<Pair> pairs = new ArrayList<>();
            list.add(pairs);
        }

        BufferedReader in = new BufferedReader(new FileReader("automaton.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("automaton.out"));

        int n, m;
        char S;

        String[] firstInput = in.readLine().split(" ");
        n = Integer.parseInt(firstInput[0]);
        S = firstInput[1].charAt(0);

        for (int i = 0; i < n; i++) {
            String[] nInput = in.readLine().split(" -> ");

            if (nInput[1].length() == 2) {
                list.get(nInput[0].charAt(0) - 'A').add(
                        new Pair(nInput[1].charAt(0) - 'a',
                                nInput[1].charAt(1) - 'A'));
            } else {
                list.get(nInput[0].charAt(0) - 'A').add(
                        new Pair(nInput[1].charAt(0) - 'a', -1));
            }
        }

        m = Integer.parseInt(in.readLine());

        for (int i = 0; i < m; i++) {
            String s = in.readLine();
            if (dfs(0, S - 'A', s, list)) {
                out.write("yes\n");
            } else out.write("no\n");
        }

        out.close();
        in.close();
    }

    private static boolean dfs(int pos, int q, String s, List<List<Pair>> list) {
        boolean result = false;
        if (pos != s.length()) {
            if (q != -1) {
                Iterator<Pair> iterator;
                for (iterator = list.get(q).iterator(); iterator.hasNext(); ) {
                    Pair to = iterator.next();
                    if (to.key == s.charAt(pos) - 'a') {
                        if (dfs(pos + 1, to.value, s, list)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        } else {
            result = q == -1;
        }
        return result;
    }

    private static class Pair {
        private int key;
        private int value;

        public Pair(int key, int value) {
            this.setKey(key);
            this.setValue(value);
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + getKey() +
                    ", second=" + getValue() +
                    '}';
        }
    }
}
