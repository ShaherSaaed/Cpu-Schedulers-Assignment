import java.util.ArrayList;
import java.util.Comparator;

public class SJFScheduler implements Scheduler {
    ArrayList<Process> processes;

    public SJFScheduler(ArrayList<Process> processes) {
        this.processes = processes;
    }

    @Override
    public void execute() {
        processes.sort(Comparator.comparingInt(Process::getBurstTime)
                .thenComparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int maxWaitingTime = 10;
        int completedProcess = 0;

        while (completedProcess < processes.size()) {
            Process selectedProcess = null;

            for (Process process : processes) {
                {
                    if (process != null && process.getArrivalTime() <= currentTime) {
                        if (selectedProcess == null || process.getBurstTime() < selectedProcess.getBurstTime()) {
                            selectedProcess = process;
                        }
                    }
                }

                if (selectedProcess == null) {
                    currentTime++;
                    continue;
                }

                if (currentTime - selectedProcess.getArrivalTime() > maxWaitingTime) {
                    selectedProcess.setBurstTime(selectedProcess.getBurstTime() - 1);
                }

                assert process != null;

                process.setStartTime(currentTime);

                currentTime += process.getBurstTime();

                selectedProcess.setCompletionTime(currentTime);

                selectedProcess.setTurnaroundTime(selectedProcess.getCompletionTime() - selectedProcess.getArrivalTime());

                selectedProcess.setWaitingTime(selectedProcess.getTurnaroundTime() - selectedProcess.getBurstTime());

                completedProcess++;

            }
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
