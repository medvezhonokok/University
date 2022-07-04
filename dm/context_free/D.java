package context_free;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 08.06.2022, среда
 **/
public class D {
    public static void main(String[] args) throws IOException, StringIndexOutOfBoundsException {
        int n, index;
        long MOD = 1000000007;
        char S;

        String universalString;

        BufferedReader in = new BufferedReader(new FileReader("nfc.in"));
        BufferedWriter out = new BufferedWriter(new FileWriter("nfc.out"));

        String[] firstInput = in.readLine().split(" ");
        n = Integer.parseInt(firstInput[0]);
        S = firstInput[1].charAt(0);

        List<List<Pair>> list = IntStream.range(0, 105).<List<Pair>>mapToObj(i -> new ArrayList<>()).collect(Collectors.toCollection(() -> new ArrayList<>(105)));

        for (int i = 0; i < n; i++) {
            universalString = in.readLine();
            index = universalString.charAt(0) - 'A';
            if (universalString.length() != 6) {
                list.get(index).add(new Pair(universalString.charAt(5) - 'A', universalString.charAt(6) - 'A'));
            } else {
                list.get(index).add(new Pair(universalString.charAt(5) - 'a', -1));
            }
        }

        String word = in.readLine();

        long[][][] dynArr = new long[101][word.length() + 10][word.length() + 10];

        for (int i = 0; i < word.length(); ++i) {
            for (int j = 0; j < 26; ++j) {
                for (int k = 0; k < list.get(j).size(); k++) {
                    Pair pair;
                    pair = list.get(j).get(k);
                    if (pair.getValue() == -1) {
                        if (word.charAt(i) - 'a' == pair.getKey()) {
                            dynArr[j][i][i] += 1;
                        }
                    }
                }
            }
        }

        int w = 1;
        while (w < word.length()) {
            for (int l = 0; !(l > word.length() - w); ++l) {
                for (int k = l; k < l + w; ++k) {
                    for (int i = 25; i >= 0; --i) {
                        int j = 0;
                        while (j < list.get(i).size()) {
                            Pair pair;
                            pair = list.get(i).get(j);
                            if (pair.getValue() != -1) {
                                dynArr[i][l][l + w] = (dynArr[i][l][l + w] + ((dynArr[pair.getKey()][l][k] * dynArr[pair.getValue()][k + 1][l + w]) % MOD)) % MOD;
                            }
                            ++j;
                        }
                    }
                }
            }
            ++w;
        }

        out.write(String.valueOf(dynArr[S - 'A'][0][word.length() - 1]));

        out.close();
        in.close();
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
