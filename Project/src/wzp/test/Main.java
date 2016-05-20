package wzp.test;

import org.springframework.aop.MethodBeforeAdvice;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * Created by WZP on 2016/3/9.
 */

public class Main {

    public  static  void  main(String  args[]) {
        StringBuffer sb = new StringBuffer();

        Scanner scaner = new Scanner(System.in);
        int times = scaner.nextInt();
        for (int time = 0; time < times; time++) {
            String pattern = scaner.next();
            String target = scaner.next();
            int length = pattern.length();
            int startIndex = 0;
            int count = 0;
            while (startIndex < target.length()) {
                int index = target.indexOf(pattern, startIndex);
                if (index >= 0) {
                    count++;
                    startIndex = index + 1;
                }
                else
                    startIndex =target.length();
            }
            System.out.println(count);
        }
    }
}
