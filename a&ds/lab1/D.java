package lab1;

import java.util.Scanner;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 23.06.2022, четверг
 **/

public class D {
    /**
     * D. K-я единица
     */
    private static long[] t;

    private static long size;

    private static long getPow2(long n) {
        int q = 1;
        if (q < n) {
            do q *= 2; while (q < n);
        }

        return q;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        long n = in.nextLong();
        long m = in.nextLong();

        size = getPow2(n);
        long[] a = new long[(int) size];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }

        foo();

        t = new long[(int) size];
        build(0, a);

        for (int i = 0; i < m; i++) {
            int step = in.nextInt();
            switch (step) {
                case 1 -> {
                    int index = in.nextInt();
                    t[(int) (size / 2 + index)] = t[(int) (size / 2 + index)] == 1 ? 0 : 1;
                    set(size / 2 + index, 0);
                }
                case 2 -> {
                    int k = in.nextInt();
                    System.out.println(findKth(0, k + 1) - size / 2);
                }
                default -> {
                }
            }
        }
    }

    private static void foo() {
        size += size - 1;
    }

    private static long set(long v, long count) {
        if (count <= 0) {
            if (t.length == 1)
                return t[(int) v];
            return set((v - 1) / 2, count + 1);
        } else {
            t[(int) v] = t[(int) (2 * v + 1)] + t[(int) (2 * v + 2)];
            if (v == 0)
                return t[(int) v];
            set((v - 1) / 2, count + 1);
        }
        return v;
    }

    private static long build(long v, long[] a) {
        if (v <= size / 2 - 1) {
            return t[(int) v] = (build(v * 2 + 1, a) + build(v * 2 + 2, a));
        } else {
            t[(int) v] = a[(int) (v - size / 2)];
            return t[(int) v];
        }
    }

    private static long findKth(long value, long k) {
        if (value > size / 2 - 1) {
            return value;
        }
        return t[(int) (value * 2 + 1)] >= k ? findKth(value * 2 + 1, k) : findKth(value * 2 + 2, k - t[(int) (value * 2 + 1)]);
    }
}
