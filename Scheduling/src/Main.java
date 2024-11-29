import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        System.out.println("CPU Scheduling Program");
        System.out.println("=======================");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.println("3. Shortest Remaining Time First (SRTF)");
        System.out.println("4. Priority Scheduling");
        System.out.println("5. FCAI Scheduler");
        System.out.print("Enter the number of the scheduling algorithm you want to use: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Input process details
        System.out.print("Enter the number of processes: ");
        int numberOfProcesses = scanner.nextInt();

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            System.out.print("Priority: ");
            int priority = scanner.nextInt();
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(id, priority, arrivalTime, burstTime));
        }

        Scheduler scheduler;
        switch (choice) {
            case 1:
                scheduler = new FCFSScheduler(processes);
                break;
            case 2:
                scheduler = new SJFScheduler(processes);
                break;
            case 3:
                scheduler = new SRTFScheduler(processes);
                break;
            case 4:
                scheduler = new PriorityScheduler(processes);
                break;
            case 5:
                scheduler = new FCAIScheduler(processes);
                break;
            default:
                System.out.println("Invalid choice. Exiting program.");
                return;
        }

        // Execute the selected scheduling algorithm
        scheduler.execute();

        // Print results
        System.out.println("Process Execution Results:");
        System.out.printf("%-10s%-15s%-15s%-15s%-15s%-15s%n",
                "Process", "Arrival Time", "Burst Time",
                "Completion Time", "Turnaround Time", "Waiting Time");
        for (Process process : processes) {
            System.out.printf("%-10d%-15d%-15d%-15d%-15d%-15d%n",
                    process.getId(), process.getArrivalTime(),
                    process.getBurstTime(), process.getCompletionTime(),
                    process.getTurnaroundTime(), process.getWaitingTime());
        }

        scanner.close();
    }
}
