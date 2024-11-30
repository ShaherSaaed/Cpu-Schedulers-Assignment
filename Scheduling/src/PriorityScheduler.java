import java.util.ArrayList;
import java.util.Comparator;

public class PriorityScheduler implements Scheduler {
    ArrayList<Process> processes;

    PriorityScheduler(ArrayList<Process> processes) {
        this.processes = processes;
    }

    @Override
    public void execute() {
        processes.sort(Comparator.comparingInt(Process::getPriority)
                .thenComparingInt(Process::getArrivalTime));
        int currentTime = 0;

        for (Process process : processes) {
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
//TODO: These need to be recalculated as they don't work as intentioned when arrival time > 0 and priority is 1, the completion time is arrival time + completion time, while it needs to be only the completion time.
            currentTime += process.getBurstTime();
            process.setCompletionTime(currentTime);

            process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());

            process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
        }
    }
}
