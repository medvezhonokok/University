package lab2;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 22.06.2022, среда
 **/
public class C {
    /**
     * С. Простое двоичное дерево поиска
     **/
    static Node root = null;

    public static class Node {
        Node left;
        Node right;

        int value;

        public Node(int v) {
            value = v;
            left = right = null;
        }

        private static Node find(int value, Node node) {
            if (node == null) return null;
            if (node.value == value) return node;
            if (value < node.value) {
                return find(value, node.left);
            } else {
                return find(value, node.right);
            }
        }

        static Node insert(int value, Node nove) {
            if (nove == null) {
                return new Node(value);
            } else if (value < nove.value) {
                nove.left = insert(value, nove.left);
            } else if (value > nove.value) {
                nove.right = insert(value, nove.right);
            }
            return nove;
        }

        static Node next(int value, Node node, Node def) {
            if (node == null) {
                return def;
            }
            if (node.value > value) {
                return next(value, node.left, node);
            } else {
                return next(value, node.right, def);
            }
        }

        static Node prev(int value, Node node, Node def) {
            if (node == null) {
                return def;
            }
            if (node.value < value) {
                return prev(value, node.right, node);
            } else {
                return prev(value, node.left, def);
            }
        }

        static Node remove(int value, Node node) {
            if (node == null)
                return null;
            if (value < node.value) {
                node.left = remove(value, node.left);
            } else if (value > node.value) {
                node.right = remove(value, node.right);
            } else if (node.left != null && node.right != null) {
                node.value = next(node.value, root, null).value;
                node.right = remove(node.value, node.right);
            } else {
                if (node.left != null) {
                    node = node.left;
                } else if (node.right != null) {
                    node = node.right;
                } else {
                    node = null;
                }
            }
            return node;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String op;
            int n;

            String[] params = line.split(" ");

            op = params[0];
            n = Integer.parseInt(params[1]);

            switch (op) {
                case "insert" -> root = Node.insert(n, root);
                case "delete" -> root = Node.remove(n, root);
                case "exists" -> System.out.println(Node.find(n, root) != null ? "true" : "false");
                case "next" -> {
                    Node r = Node.next(n, root, null);
                    System.out.println(r == null ? "none" : r.value);
                }
                case "prev" -> {
                    Node node = Node.prev(n, root, null);
                    System.out.println(node == null ? "none" : node.value);
                }
            }
        }

        in.close();
    }
}
