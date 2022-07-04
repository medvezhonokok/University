package lab2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 22.06.2022, среда
 **/
public class B {
    /**
     * B. Постройте какое-нибудь BST-дерево
     **/
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        int n = Integer.parseInt(in.nextLine());
        int[] array = new int[n];

        String[] params = in.nextLine().split(" ");

        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(params[i]);
        }

        Arrays.sort(array);

        System.out.println(array[array.length - 1]);
        for (int i = 1; i < array.length; i++) {
            System.out.println(array[i - 1] + " -1 " + array[i]);
        }

        System.out.println(array[array.length - 1] + " -1 -1");

        System.out.println(array[0]);
    }
}
