import java.io.*;
import java.util.HashMap;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;

public class CommandExecutor {

    protected HashMap<String, String> variables;

    public CommandExecutor() {
        variables = new HashMap<>();
    }

    public void executeCommand(String command) {
        String[] parts = command.split(" ");
        String variable = parts[1];

        switch (parts[0]) {
            case "assign":
                if (parts[2].equals("input")) {

                    System.out.print("Enter value for " + variable + ": ");
                    Scanner sc = new Scanner(System.in);
                    String value = sc.nextLine();
                    variables.put(variable, value);
                } else {
                    if (parts[2].equals("readFile")) {
                        String value = variables.get(parts[3]);
                        String s2 = "";
                        try (BufferedReader br = new BufferedReader(new FileReader(value))) {
                            String line;


                            while ((line = br.readLine()) != null) {
                                s2 += line + "\n";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        variables.put(variable, s2);
                    } else {
                        String value = evaluateExpression(parts) + " ";
                        variables.put(variable, value);
                    }
                }
                break;
            case "print":
                String result = variables.get(variable);
                System.out.println(variable + ": " + result);
                break;
            case "writeFile":
                try {

                    String filePath = variables.get(parts[1]);


                    File file = new File(filePath + ".txt");


                    if (file.createNewFile()) {
                        System.out.println("File created successfully");

                    } else {
                        System.out.println("File already exists");
                    }

                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        System.out.println(variables.get(parts[2]));
                        writer.write(variables.get(parts[2]), 0, variables.get(parts[2]).length());
                        writer.close();
                    }
                    catch(IOException e){
                        System.out.println("An error occurred while finding the file.");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid command: " + command);
        }
    }

    private double evaluateExpression(String[] parts) {
        int operand1 = Integer.parseInt(variables.get(parts[3]));
        int operand2 = Integer.parseInt(variables.get(parts[4]));

        switch (parts[2]) {
            case "add":
                return operand1 + operand2;
            case "subtract":
                return operand1 - operand2;
            case "multiply":
                return operand1 * operand2;
            case "divide":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid expression");
        }
    }

    public void executeCommandsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                executeCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}