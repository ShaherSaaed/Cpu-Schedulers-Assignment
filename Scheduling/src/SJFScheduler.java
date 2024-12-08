import java.util.ArrayList;
import java.util.Comparator;

public class SJFScheduler implements Scheduler {
    ArrayList<Process> processes;

    public SJFScheduler(ArrayList<Process> processes) {
        this.processes = processes;
    }

    @Override
    public void execute() {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;
        int n = processes.size();

        while (completedProcesses < n) {
            Process selectedProcess = null;

            for (Process process : processes) {
                if (!process.isCompleted && process.getArrivalTime() <= currentTime) {
                    if (selectedProcess == null || process.getBurstTime() < selectedProcess.getBurstTime()) {
                        selectedProcess = process;
                    }
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

            currentTime += selectedProcess.getBurstTime();
            selectedProcess.setCompletionTime(currentTime);
            selectedProcess.setTurnaroundTime(selectedProcess.getCompletionTime() - selectedProcess.getArrivalTime());
            selectedProcess.setWaitingTime(selectedProcess.getTurnaroundTime() - selectedProcess.getBurstTime());
            selectedProcess.isCompleted = true;
            completedProcesses++;
        }
    }

    @Override
    public void setVisible(boolean b) {
    }

    @Override
    public void updateExecutionHistory() {
    }

    @Override
    public void updateStatistics(String scheduler, int n, double a, double b) {
    }
}
