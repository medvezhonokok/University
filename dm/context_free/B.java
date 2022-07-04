package context_free;

import java.io.*;
import java.util.*;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 13.06.2022, понедельник
 **/
public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("epsilon.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("epsilon.out"));

        Set<String> nt = new TreeSet<>();
        List<Pair> list = new ArrayList<>();

        int[] e1 = new int[1005];
        int[] e2 = new int[1005];

        int n;

        String[] firstLine = in.readLine().split(" ");
        n = Integer.parseInt(firstLine[0]);

        for (int i = 0; i < n; i++) {
            String[] nextInput = in.readLine().split(" ");
            if (nextInput.length == 2) {
                list.add(new Pair(nextInput[0], ""));
            } else {
                list.add(new Pair(nextInput[0], nextInput[2]));
            }
        }

        boolean flag = true;

        while (flag) {
            flag = false;
            for (int i = 0; i < n; i++) {
                if (e1[i] == 0) {
                    int count = 0;
                    var p = list.get(i);

                    for (int j = 0; j < p.getT2().length(); j++) {
                        if (e2[p.X(j)] == 0) {
                            count = 1;
                            break;
                        }
                    }

                    if (count == 0) {
                        e1[i] += 1;
                        e2[p.Y(0)] += 1;
                        nt.add(p.getT1());
                        flag = true;
                    }
                }
            }
        }

        Iterator<String> iterator;
        for (iterator = nt.iterator(); iterator.hasNext(); ) {
            var s = iterator.next();
            out.write(s + " ");
        }

        in.close();
        out.close();
    }

    private static class Pair {
        private String t1;
        private String t2;

        public char X(int i) {
            return t2.charAt(i);
        }

        public char Y(int i) {
            return t1.charAt(i);
        }

        public Pair(String t1, String t2) {
            this.setT1(t1);
            this.setT2(t2);
        }

        public String getT1() {
            return t1;
        }

        public String getT2() {
            return t2;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first='" + getT1() + '\'' +
                    ", second='" + getT2() + '\'' +
                    '}';
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public void setT2(String t2) {
            this.t2 = t2;
        }
    }
}