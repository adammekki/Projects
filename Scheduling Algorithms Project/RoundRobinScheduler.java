import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RoundRobinScheduler extends CommandExecutor{

    static int pid =0;
    ReadyQueue readyQueue;
    ArrayList<Integer> indexes = new ArrayList<Integer>();
    GanttChart g;

    RoundRobinScheduler()
    {
        readyQueue = new ReadyQueue();
        indexes = new ArrayList<Integer>();
        g = new GanttChart();
    }

    public void addProcess(String filePath)
    {
        ArrayList<String> p = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;


            while ((line = br.readLine()) != null) {
                p.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        readyQueue.enqueue(p);
        indexes.add(++pid);
    }

    public void executeProcesses(int q) {
int startTime = 0;
        while(!readyQueue.isEmpty())
        {
            int counter = 0;
            int startCounter = 0;
            System.out.println("Ready Queue");

            for(int i = 0; i < indexes.size(); i++)
            {
                System.out.print(indexes.get(i) + " ");
            }

            System.out.println("");

            System.out.println("Executing process" + " " + indexes.get(0));

            System.out.println("Memory State:");
            for (String variable : variables.keySet()) {
                String value = variables.get(variable);
                System.out.print(variable + " = " + value);
                System.out.println("");
            }

            ArrayList<String> temp = readyQueue.dequeue();
            int current = indexes.remove(0);
            g.addTask("Process " + indexes.get(0), startTime, q);

            startTime += 2;
            for(int i = 0; i < q; i++)
            {

                String command;

                if(temp.get(i) != null) {
                    command = temp.remove(0);
                    executeCommand(command);
                    if(temp.isEmpty())
                    {
                        break;
                    }
                }
            }
            g.displayChart();
            if(!temp.isEmpty()) {
                readyQueue.enqueue(temp);
                indexes.add(current);
            }
            System.out.println(counter++);
        }

    }

    public static void main(String[] args) {
        RoundRobinScheduler rrs = new RoundRobinScheduler();
        rrs.addProcess("src\\Program_1.txt");
        rrs.addProcess("src\\Program_2.txt");
        rrs.addProcess("src\\Program_3.txt");

        rrs.executeProcesses(2);
    }
}