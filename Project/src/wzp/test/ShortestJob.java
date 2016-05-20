package wzp.test;

import jdk.nashorn.internal.scripts.JO;

import java.util.*;

/**
 * Created by WZP on 2016/4/21.
 */

class Job implements Comparable<Job> {
    int requestTime;
    int durations;
    public Job(int requestTime, int durations) {
        this.requestTime = requestTime;
        this.durations = durations;
    }

    @Override
    public int compareTo(Job o) {
        if (durations != o.durations)
            return -(durations - o.durations);
        else
            return -(requestTime - o.durations);
    }
}

public class ShortestJob {
    public static float waitingTimeSJF(int[] requestTimes, int[] durations) {
        List<Job> jobList = new ArrayList<Job>();
        int n = Math.min(requestTimes.length, durations.length);
        for (int i = 0; i < n; i++) {
            jobList.add(new Job(requestTimes[i], durations[i]));
        }
        Collections.sort(jobList, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return o1.requestTime-o2.durations;
            }
        });
        int curTime = 0;
        PriorityQueue<Job> jobHeap = new PriorityQueue<Job>();
        int index = 0;
        int res = 0;
        while (true) {
            for (int i = index; i < n; i++) {
                if (jobList.get(i).requestTime > curTime)
                    break;
                jobHeap.add(jobList.get(i));
            }
            if (jobHeap.isEmpty()) break;
            // 要处理的任务
            Job job = jobHeap.poll();
            curTime += job.durations;
            res += curTime - job.requestTime;
        }
        return (res*1.0f)/(n*1.0f);
    }
}
