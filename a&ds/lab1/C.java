package lab1;

import java.util.Scanner;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 23.06.2022, четверг
 **/
public class C {
    /**
     * C. Отрезок с максимальной суммой
     */
    public static class Data {
        public long sum, pref, s, ans;

        public Data(long sum, long pref, long s, long ans) {
            this.sum = sum;
            this.pref = pref;
            this.s = s;
            this.ans = ans;
        }
    }

    public static int n;
    public static int m;

    public static long[] a;
    public static Data[] t;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        m = in.nextInt();

        a = new long[(int) n];
        t = new Data[(int) (4 * n)];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        build(a, 1, 0, n - 1);

        var data = res(1, 0, n - 1, 0, n - 1);
        System.out.println(data.ans);

        for (long i = 0; i < m; i++) {
            int index = in.nextInt();
            int value = in.nextInt();
            set(1, 0, n - 1, index, value);
            data = res(1, 0, n - 1, 0, n - 1);
            System.out.println(data.ans);
        }
    }

    public static Data foo(Data l, Data r) {
        var res = new Data(0, 0, 0, 0);
        res.sum = l.sum + r.sum;
        res.pref = Math.max(l.pref, l.sum + r.pref);
        res.s = Math.max(r.s, r.sum + l.s);
        res.ans = Math.max(Math.max(l.ans, r.ans), l.s + r.pref);
        return res;
    }

    public static Data make(long val) {
        var res = new Data(0, 0, 0, 0);
        res.sum = val;
        res.pref = res.s = res.ans = Math.max(0, val);
        return res;
    }

    public static Data res(long v, long xl, long xr, long l, long r) {
        if (l == xl && xr == r)
            return t[(int) v];
        if (r <= (xl + xr) / 2)
            return res(v * 2, xl, (xl + xr) / 2, l, r);
        if (l > (xl + xr) / 2)
            return res(v * 2 + 1, (xl + xr) / 2 + 1, xr, l, r);
        return foo(res(v * 2, xl, (xl + xr) / 2, l, (xl + xr) / 2),
                res(v * 2 + 1, (xl + xr) / 2 + 1, xr, (xl + xr) / 2 + 1, r)
        );
    }

    public static void build(long[] a, long v, long xl, long xr) {
        if (xl == xr)
            t[(int) v] = make(a[(int) xl]);
        else {
            build(a, v * 2, xl, (xl + xr) / 2);
            build(a, v * 2 + 1, (xl + xr) / 2 + 1, xr);
            t[(int) v] = foo(t[(int) (v * 2)], t[(int) (v * 2 + 1)]);
        }
    }

    public static void set(long v, long xl, long xr, long index, long new_val) {
        if (xl == xr)
            t[(int) v] = make(new_val);
        else {
            if (index <= (xl + xr) / 2)
                set(v * 2, xl, (xl + xr) / 2, index, new_val);
            else
                set(v * 2 + 1, (xl + xr) / 2 + 1, xr, index, new_val);
            t[(int) v] = foo(t[(int) (v * 2)], t[(int) (v * 2 + 1)]);
        }
    }
}
