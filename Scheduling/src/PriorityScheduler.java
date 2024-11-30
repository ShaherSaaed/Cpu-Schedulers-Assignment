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

            currentTime += process.getBurstTime();

            process.setCompletionTime(currentTime);

            process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());

            process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
        }
    }
}
