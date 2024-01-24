import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SJF extends CommandExecutor{

    ArrayList<ArrayList<String>> processesArray;
    ArrayList<Integer> indexes;
    ArrayList<String> ganttChart;
    static int pid =0;
    GanttChart g;
    public SJF()
    {
        processesArray = new ArrayList<ArrayList<String>>();
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
        processesArray.add(p);
        indexes.add(++pid);
        
    }

    public void sort()
    {


        for(int i = 0; i< processesArray.size(); i++)
        {
            for(int j = 0; j< processesArray.size()-1; j++)
            {
                if(processesArray.get(j).size() > processesArray.get(j+1).size())
                {
                    ArrayList<String> temp = processesArray.get(j);
                    int x = indexes.get(j);
                    processesArray.remove(j);
                    indexes.remove(j);
                    processesArray.add(temp);
                    indexes.add(x);
                    
                }
            }
        }

    }

    public void executeProcesses() {
        int startTime = 0;

        System.out.println("Ready Queue");
        for (int i = 0; i < indexes.size(); i++) {
            System.out.print(indexes.get(i) + " ");
        }
        System.out.println("");
        this.sort();

        for (ArrayList<String> process : processesArray) {
            System.out.println("Ready Queue");
            for (int i = 0; i < indexes.size(); i++) {
                System.out.print(indexes.get(i) + " ");




            }
            System.out.println();



               g.addTask("Process " + indexes.get(0), startTime, process.size());
               System.out.println(process.size());
               startTime += processesArray.get(0).size();

                System.out.println("");
                System.out.println("Executing process" + " " + indexes.get(0));

                System.out.println("Memory State:");
                for (String variable : variables.keySet()) {
                    String value = variables.get(variable);
                    System.out.print(variable + " = " + value);
                    System.out.println("");
                }

                for (String line : process) {
                    executeCommand(line);
                }
                indexes.remove(0);

                g.displayChart();

            }

        }


    public static void main(String[] args)
    {
        SJF sjf = new SJF();

        sjf.addProcess("src\\Program_1.txt");
        sjf.addProcess("src\\Program_2.txt");
        sjf.addProcess("src\\Program_3.txt");

        sjf.executeProcesses();
        
        
    }

}