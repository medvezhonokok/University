package lab1;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 23.06.2022, четверг
 **/

import java.util.Scanner;

public class A {
    /**
     * A. Дерево отрезков на сумму
     **/
    public static int n;
    public static int k;

    public static long[] a;
    public static long[] t;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        k = in.nextInt();

        a = new long[n];
        t = new long[4 * n];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }

        build(a, 1, 0, n - 1);

        for (int i = 0; i < k; i++) {
            int step = in.nextInt();
            switch (step) {
                case 1 -> {
                    int index = in.nextInt();
                    int value = in.nextInt();
                    set(1, 0, n - 1, index, value);
                }
                case 2 -> {
                    int l = in.nextInt();
                    int r = in.nextInt();
                    System.out.println(sum(1, 0, n - 1, l, r - 1));
                }
            }
        }
    }

    public static void build(long[] a, int v, int tl, int tr) {
        if (tl == tr)
            t[v] = a[tl];
        else {
            int tm = (tl + tr) / 2;
            build(a, v * 2, tl, tm);
            build(a, v * 2 + 1, tm + 1, tr);
            t[v] = t[v * 2] + t[v * 2 + 1];
        }
    }

    public static long sum(int x, int xl, int xr, int l, int r) {
        if (l > r)
            return 0;
        if (l == xl && r == xr)
            return t[x];
        int tm = (xl + xr) / 2;
        return sum(x * 2, xl, tm, l, Math.min(r, tm))
                + sum(x * 2 + 1, tm + 1, xr, Math.max(l, tm + 1), r);
    }

    public static void set(int v, int xl, int xr, int index, int new_val) {
        if (xl == xr)
            t[v] = new_val;
        else {
            int tm = (xl + xr) / 2;
            if (index <= tm)
                set(v * 2, xl, tm, index, new_val);
            else
                set(v * 2 + 1, tm + 1, xr, index, new_val);
            t[v] = t[v * 2] + t[v * 2 + 1];
        }
    }
}

