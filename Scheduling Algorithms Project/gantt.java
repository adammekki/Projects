import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

class GanttChart {
    private List<Task> tasks;

    public GanttChart() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String name, int startTime, int duration) {
        Task task = new Task(name, startTime, duration);
        tasks.add(task);
    }
    private int calculateTotalTime() {
        int totalTime = 0;
        for (Task task : tasks) {
            totalTime = Math.max(totalTime, task.getStartTime() + task.getDuration());
        }
        return totalTime;
    }
    public void displayChart() {
        int totalTime = calculateTotalTime();


        System.out.print("Time | ");
        System.out.print("     ");
        for (int i = 0; i < totalTime; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();


        for (Task task : tasks) {
            System.out.print(task.getName() + " | ");
            for (int i = 0; i < totalTime; i++) {
                if (i >= task.getStartTime() && i < task.getStartTime() + task.getDuration()) {
                    System.out.print("*\t");
                } else {
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }

}

class Task {
    private String name;
    private int startTime;
    private int duration;

    public Task(String name, int startTime, int duration) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return startTime + duration;
    }
    public int getDuration() {
        return duration;
    }
}
