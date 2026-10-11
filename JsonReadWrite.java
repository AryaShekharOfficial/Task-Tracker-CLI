import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class JsonReadWrite {
    public static void main(String args[]){
        int choice = 1;
        if (choice == 0){
            JsonWrite jw = new JsonWrite();
            jw.addTask();
        }
        else{
            //JsonRead jr = new JsonRead();
            TaskDisplay.displayTasks();
        }
        
    }
    
}

//To write to a Json file
class JsonWrite{
    public void addTask(){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String writeProperties[] = new String[2]; // will be used to hold name and description of the new task
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int prevId; // to store previous id
        try(Scanner ob = new Scanner(System.in)){ // takes name and description from the user
            System.out.print("Enter name of the Task: ");
            writeProperties[0] = ob.nextLine();
            System.out.print("Enter description of the Task: ");
            writeProperties[1] = ob.nextLine();
        }
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks 
        if (tasks.size() > 0){ // if tasks exist last Id is searched and stored as previous id
            prevId = Integer.parseInt((tasks.get(tasks.size() - 1)).getId());
        }
        else{ // if no tasks exist previous id is set to 0
            prevId = 0;
        }
        // an initial [ is added to toWrite
        toWrite += """
            [
                """;
        int newId = ++prevId; // the new id will obviously be previoius id + 1
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            toWrite += """
                    {
                        "###id" : "%s###",
                        "###name" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getName(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        // adds the new task at the end of toWrite
        toWrite +=  """
                {
                    "###id" : "%s###",
                    "###name" : "%s###",
                    "###description" : "%s###",
                    "###timeCreated" : "%s###",
                    "###timeUpdated" : "%s###",
                    "###status" : "%s###"
                }
            ]""".formatted(Integer.toString(newId), writeProperties[0], writeProperties[1], Instant.now().toString(), Instant.now().toString(), "todo");
    try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to tasks.json
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }
}

//To Read from a Json file
class JsonRead{
    public static List<Task> read(){
        Path filepath = Paths.get("tasks.json");
        List<Task> tasks = new ArrayList<>();
        try{
            String readFile = Files.readString(filepath);
            tasks = TasksParser.handleparse(readFile);
        } catch (Exception e) {
            System.out.println("Error Reading File: " + e);
        }
        return tasks;
    }
}

class TaskDisplay{

    // display a single task using getters to Task
    private static void displayTask(Task task){
        System.out.println("Id: " + task.getId());
        System.out.println("Name: " + task.getName());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Created: " + task.getTimeCreated());
        System.out.println("Last Updated: " + task.getTimeUpdated());
        System.out.println("Status: " + task.getStatus());
    }

    //displays all tasks
    public static void displayTasks(){
        List<Task> tasks = JsonRead.read(); // calls the read function of JsonRead class to get the ArrayList of all Task Objects
        int i = 0;
        for (Task t: tasks){ // loops thorugh the list and displays the tasks one by one using displayTask() function
            i++;
            System.out.println("— — — — — TASK %d — — — — —".formatted(i));
            displayTask(t);
        }
    }
}