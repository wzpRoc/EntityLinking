package wzp.test;

import java.io.IOException;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            int n = in.nextInt();
            if (!sub(n)) break;
        }
    }

    private static boolean sub(int n) {
        if (n==1)
            System.out.println(-1);
        boolean[] dp = new boolean[n+1];
        TreeSet<Integer> set = new TreeSet<Integer>();
        
        return n!=-1;
    }
}