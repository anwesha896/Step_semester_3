
import java.util.*;

class LabJobEntry {
    String name;
    int remainingTime;

    LabJobEntry(String name, int time) {
        this.name = name;
        remainingTime = time;
    }
}

class RoundRobinLabSchedulerEngine {
    Queue<LabJobEntry> queue = new LinkedList<>();

    void addJob(String name, int time) {
        queue.add(new LabJobEntry(name, time));
    }

    void schedule(int quantum) {
        int currentTime = 0;
        StringBuilder slices = new StringBuilder();
        StringBuilder finish = new StringBuilder();

        while (!queue.isEmpty()) {
            LabJobEntry job = queue.remove();

            int runTime = Math.min(quantum, job.remainingTime);
            int start = currentTime;

            currentTime += runTime;
            job.remainingTime -= runTime;

            slices.append(job.name)
                    .append("(").append(start)
                    .append("-").append(currentTime).append(") ");

            if (job.remainingTime > 0) {
                queue.add(job);
            } else {
                if (finish.length() > 0) {
                    finish.append(", ");
                }

                finish.append(job.name)
                        .append(" ").append(currentTime);
            }
        }

        System.out.println(slices.toString().trim());
        System.out.println("finish " + finish);
    }
}

public class RoundRobinLabScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int quantum = sc.nextInt();
        int n = sc.nextInt();

        RoundRobinLabSchedulerEngine scheduler =
                new RoundRobinLabSchedulerEngine();

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int time = sc.nextInt();

            scheduler.addJob(name, time);
        }

        scheduler.schedule(quantum);

        sc.close();
    }
}
