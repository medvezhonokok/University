package lab1;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 23.06.2022, четверг
 **/

import java.util.Scanner;

public class B {
    /**
     * B. Число минимумов на отрезке
     */
    static class Pair {
        public long a;
        public long b;

        public Pair(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    public static int n;
    public static int k;

    public static long[] a;
    public static Pair[] t;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String nums = in.nextLine();
        String num1 = "";
        StringBuilder num2 = new StringBuilder();
        int index = 0;

        for (int i = 0; i < nums.length(); i++) {
            if (nums.charAt(i) != ' ') {
                num1 += nums.charAt(i);
            } else {
                index = i + 1;
                n = Integer.parseInt(num1);
                break;
            }
        }

        for (int i = index; i < nums.length(); i++) {
            num2.append(nums.charAt(i));
        }

        k = Integer.parseInt(num2.toString());

        a = new long[n];
        t = new Pair[4 * n];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }

        build(a, 1, 0, n - 1);

        for (int i = 0; i < k; i++) {
            int step = in.nextInt();
            switch (step) {
                case 1 -> {
                    index = in.nextInt();
                    int value = in.nextInt();
                    set(1, 0, n - 1, index, value);
                }
                case 2 -> {
                    int l = in.nextInt();
                    int r = in.nextInt();
                    Pair out = getMin(1, 0, n - 1, l, r - 1);
                    System.out.print(out.a + " " + out.b + "\n");
                }
            }
        }
    }

    public static void build(long[] a, int v, int tl, int tr) {
        if (tl == tr)
            t[v] = new Pair(a[tl], 1);
        else {
            int tm = (tl + tr) / 2;
            build(a, v * 2, tl, tm);
            build(a, v * 2 + 1, tm + 1, tr);
            t[v] = foo(t[v * 2], t[v * 2 + 1]);
        }
    }

    private static Pair foo(Pair p, Pair p1) {
        if (p.a <= p1.a) {
            return p1.a > p.a ? p : new Pair(p.a, p.b + p1.b);
        } else {
            return p1;
        }
    }

    static Pair getMin(long v, long tl, long tr, long l, long r) {
        if (l > r)
            return new Pair(Integer.MAX_VALUE, 0);
        if (l == tl && r == tr)
            return t[(int) v];
        long tm = (tl + tr) / 2;
        return foo(
                getMin(v * 2, tl, tm, l, Math.min(r, tm)),
                getMin(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r)
        );
    }

    public static void set(int v, int xl, int xr, int index, int new_val) {
        if (xl == xr)
            t[v] = new Pair(new_val, 1);
        else {
            int tm = (xl + xr) / 2;
            if (index <= tm)
                set(v * 2, xl, tm, index, new_val);
            else
                set(v * 2 + 1, tm + 1, xr, index, new_val);
            t[v] = foo(t[v * 2], t[v * 2 + 1]);
        }
    }
}

