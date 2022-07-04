package lab2;

import java.util.Scanner;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 22.05.2022, воскресенье
 **/
public class A {
    /**
     * (A) BST или нет?
     **/
    private static Node[] nodes;

    private static class Node {
        int key;
        int left;
        int right;

        public Node(int key, int left, int right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        nodes = new Node[n + 1];

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int l = sc.nextInt();
            int r = sc.nextInt();

            nodes[i + 1] = new Node(x, l, r);
        }

        int num = sc.nextInt();

        System.out.println(isBST(num) ? "YES" : "NO");
    }

    private static boolean isBST(int koren) {
        return check(koren, 0, Integer.MAX_VALUE);
    }

    private static boolean check(int i, int min, int max) {
        if (i == -1) return true;

        int value, r, l;

        value = nodes[i].key;
        r = nodes[i].right;
        l = nodes[i].left;

        if (value >= max || value <= min) return false;

        return check(l, min, value) && check(r, value, max);
    }
}
