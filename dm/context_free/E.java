package context_free;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : medvezhonokok
 * @mailto : nocap239@gmail.com
 * @created : 19.06.2022, воскресенье
 **/
public class E {
    public static void main(String[] args) throws IOException {
        solve();
    }

    static void solve() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("cf.in"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("cf.out"));

        String[] line = reader.readLine().split(" ");

        String str;
        var c = 123;
        int n = Integer.parseInt(line[0]);
        int start = line[1].charAt(0) - '0';

        var lists = new ArrayList<ArrayList<ArrayList<Integer>>>();
        var e = new ArrayList<Integer>();

        for (int i = 0; i < 1000; i++) {
            lists.add(i, new ArrayList<>());
            e.add(0);
        }

        for (int i = 0; i < n; ++i) {
            str = reader.readLine();
            int index = str.charAt(0) - '0';

            if (str.length() <= 4) {
                str = "";
            } else {
                str = str.substring(5);
            }
            ArrayList<Integer> list = new ArrayList<>();

            if (str.length() != 0) {
                if (str.length() <= 1) {
                    list.add(1);
                    list.add((int) str.charAt(0));
                    lists.get(index).add(list);
                } else {
                    int cur = index;

                    for (int j = str.length() - 3; j >= 0; --j) {
                        list = new ArrayList<>();
                        list.add(2);
                        list.add((int) str.charAt(j));
                        list.add(c);

                        lists.get(cur).add(list);
                        cur = c++;
                    }

                    list = new ArrayList<>();
                    list.add(2);
                    list.add((int) str.charAt(str.length() - 2));
                    list.add((int) str.charAt(str.length() - 1));

                    lists.get(cur).add(list);
                }
            } else {
                e.set(index, 1);
                list.add(0);
                lists.get(index).add(list);
            }
        }

        boolean flag = true;
        int j = 0;
        while (flag && j < 1000) {
            flag = false;
            int i = 999;
            while (i >= 0) {
                for (Iterator<ArrayList<Integer>> iterator = lists.get(i).iterator(); iterator.hasNext(); ) {
                    ArrayList<Integer> list = iterator.next();
                    if (list.size() > 0) {
                        if (list.get(0) == 1) {
                            if (e.get(list.get(1) - '0') == 1) {
                                e.set(i, 1);
                                flag = true;
                            }
                        } else if (e.get(list.get(1) - '0') * e.get(list.get(list.size() - 1) - '0') == 1 && list.get(0) == 2) {
                            e.set(i, 1);
                            flag = true;
                        }
                    }
                }
                --i;
            }
            ++j;
        }

        String word = reader.readLine();
        int size = word.length();

        int[][][] graph = new int[1000][size][size];

        int i = 0;
        while (i < 1000) {
            int l = 0;
            while (l < size) {
                int r = 0;
                while (r < size) {
                    graph[i][l][r] = 0;
                    r++;
                }
                l++;
            }
            i++;
        }

        {
            int l = size - 1;
            while (l >= 0) {
                calc(lists, word, graph, l);
                sup(lists, e, graph, l, l);
                graph[word.charAt(l) - '0'][l][l] = 1;
                l = l - 1;
            }
        }

        {
            int d = 1;
            while (d < size) {
                for (int l = size - d - 1; l >= 0; l--) {
                    pred(lists, e, graph, l, l + d);
                }
                d++;
            }
        }

        writer.write(graph[start][0][size - 1] == 1 ? "yes" : "no");

        writer.close();
        reader.close();
    }

    private static void pred(ArrayList<ArrayList<ArrayList<Integer>>> lists,
                             ArrayList<Integer> e,
                             int[][][] graph,
                             int a,
                             int b) {
        int i = 999;
        while (i >= 0) {
            boolean flag = false;

            for (Iterator<ArrayList<Integer>> iterator = lists.get(i).iterator(); iterator.hasNext(); ) {
                ArrayList<Integer> list = iterator.next();
                if (list.size() > 0) {
                    if (list.get(0) != 2) {
                        i--;
                        continue;
                    }
                    int l = a;
                    if (l < b) {
                        do {
                            int c = graph[list.get(1) - '0'][a][l] * graph[list.get(list.size() - 1) - '0'][l + 1][b] == 1 ? 1 : 0;
                            flag = flag || (c > 0);
                            l++;
                        } while (l < b);
                    }
                }
            }

            graph[i][a][b] = flag ? 1 : 0;
            i--;
        }

        sup(lists, e, graph, a, b);
    }

    static void calc(ArrayList<ArrayList<ArrayList<Integer>>> lists,
                     String str,
                     int[][][] graph,
                     int a) {
        int i = 999;
        while (i >= 0) {
            for (Iterator<ArrayList<Integer>> iterator = lists.get(i).iterator(); iterator.hasNext(); ) {
                ArrayList<Integer> list = iterator.next();
                if (list.size() > 0) {
                    if (list.get(0) == 1) {
                        if (list.get(1) == (int) str.charAt(a)) {
                            graph[i][a][a] = 1;
                            break;
                        }
                    }
                }
            }
            i--;
        }
    }

    private static void sup(ArrayList<ArrayList<ArrayList<Integer>>> lists,
                            List<Integer> e,
                            int[][][] graph,
                            int a,
                            int b) {
        func(lists, e, graph, a, b, true);
    }

    private static void func(ArrayList<ArrayList<ArrayList<Integer>>> lists,
                             List<Integer> e,
                             int[][][] graph,
                             int a,
                             int b,
                             boolean flag) {
        int j = 0;

        while (flag && j < 1000) {
            flag = false;
            int i = 999;
            while (i >= 0) {
                for (Iterator<ArrayList<Integer>> iterator = lists.get(i).iterator(); iterator.hasNext(); ) {
                    ArrayList<Integer> list = iterator.next();
                    flag = check(e, graph, a, b, flag, i, list);
                }
                i--;
            }
            j++;
        }
    }

    private static boolean check(List<Integer> e, int[][][] graph, int a, int b, boolean flag, int i, ArrayList<Integer> list) {
        if (list.size() > 0) {
            if (list.get(0) == 2 &&
                    (e.get(list.get(1) - '0') == 1 &&
                            graph[list.get(list.size() - 1) - '0'][a][b] == 1 ||
                            e.get(list.get(list.size() - 1) - '0') == 1 &&
                                    graph[list.get(1) - '0'][a][b] == 1) || list.get(0) == 1 &&
                    graph[list.get(list.size() - 1) - '0'][a][b] == 1) {
                graph[i][a][b] = 1;
                flag = true;
            }
        }
        return flag;
    }
}
