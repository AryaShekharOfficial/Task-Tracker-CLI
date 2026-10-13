import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class JsonReadWrite {
    public static void main(String args[]){
        int choice;
        Scanner ob = new Scanner(System.in);
        do{
            System.out.println("MENU: ");
            System.out.println("1. Add Task");
            System.out.println("2. Display All Task");
            System.out.println("3. Delete A Task");
            System.out.println("4. Update A Task");
            System.out.println("5. Mark A Task As in Progress");
            System.out.println("6. Mark A Task As Done");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");
            choice = ob.nextInt();
            ob.nextLine();
            if (choice == 1){
                JsonWrite.addTask(ob);
            } else if (choice == 2){
                TaskDisplay.displayTasks();
            } else if (choice == 3){
                JsonWrite.deleteTask(ob);
            } else if (choice == 4){
                JsonWrite.updateTask(ob);
            } else if (choice == 5){
                JsonWrite.markTaskAsInProgress(ob);
            } else if (choice == 6){
                JsonWrite.markTaskAsDone(ob);
            }
        } while (choice != 7);
        ob.close();
    }
    
}

//To write to a Json file
class JsonWrite{
    // Function to add a task
    public static void addTask(Scanner ob){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String desc = ""; // will be used to hold name and description of the new task
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int prevId; // to store previous id
        // Take the description from the user
        System.out.print("Enter description of the Task: ");
        desc = ob.nextLine();
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
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        // adds the new task at the end of toWrite
        toWrite +=  """
                {
                    "###id" : "%s###",
                    "###description" : "%s###",
                    "###timeCreated" : "%s###",
                    "###timeUpdated" : "%s###",
                    "###status" : "%s###"
                }
            ]""".formatted(Integer.toString(newId), desc, Instant.now().toString(), Instant.now().toString(), "todo");
    try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to tasks.json
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }

    // Function to update a task
    public static void updateTask(Scanner ob){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int updateId; //will hold the id of the task needs to be updated
        System.out.print("Enter the id of the task you want to update: ");
        updateId = ob.nextInt(); // takes the id of the task to be updated from the user
        ob.nextLine();
        String newDesc;
        System.out.print("Enter the new description of the task: ");
        newDesc = ob.nextLine();
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        // an initial [ is added to toWrite
        toWrite += """
            [
                """;
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            if (Integer.parseInt(task.getId()) == updateId){ 
                /*if the id of the task in the ArrayList matches with 
                the id of the task to updated, the description of the task is
                set to newDesc and the update time is also updated*/
                task.setDescription(newDesc);
                task.setTimeUpdated(Instant.now().toString());
            }
            toWrite += """
                    {
                        "###id" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  """

            ]"""; // adds the closing bracket ]
        try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to tasks.json
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }

    // Function to delete a task
    public static void deleteTask(Scanner ob){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int delId; //will hold the id of the task needs to be deleted
        System.out.print("Enter the id of the task you want to delete: ");
        delId = ob.nextInt(); // takes the id of the task to be deleted from the user
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        // an initial [ is added to toWrite
        toWrite += """
            [
                """;
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            if (Integer.parseInt(task.getId()) == delId){ 
                /*if the id of the task in the ArrayList matches with 
                the id of the task to deleted, the status of the task is 
                assigned to "deleted" and all its other values are set to null*/
                task.setStatus("deleted");
                task.setDescription(null);
                task.setTimeCreated(null);
                task.setTimeUpdated(null);
            }
            toWrite += """
                    {
                        "###id" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  """

            ]"""; // adds the closing bracket ]
        try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to tasks.json
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }

    // Function to mark a task as in progress
    public static void markTaskAsInProgress(Scanner ob){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int markId; //will hold the id of the task needs to be marked
        System.out.print("Enter the id of the task you want to update: ");
        markId = ob.nextInt(); // takes the id of the task to be updated from the user
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        // an initial [ is added to toWrite
        toWrite += """
            [
                """;
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            if (Integer.parseInt(task.getId()) == markId){ 
                /*if the id of the task in the ArrayList matches with 
                the id of the task to updated, the description of the task is
                set to newDesc and the update time is also updated*/
                task.setStatus("in progress");
            }
            toWrite += """
                    {
                        "###id" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  """

            ]"""; // adds the closing bracket ]
        try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to tasks.json
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }

    // Function to mark a task as done
    public static void markTaskAsDone(Scanner ob){
        Path filePath = Paths.get("tasks.json"); // gets the tasks.json file as Path object
        String toWrite = """
                
                """; //will hold the entire task string to be written to tasks.json
        int markId; //will hold the id of the task needs to be marked
        System.out.print("Enter the id of the task you want to update: ");
        markId = ob.nextInt(); // takes the id of the task to be updated from the user
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        // an initial [ is added to toWrite
        toWrite += """
            [
                """;
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            if (Integer.parseInt(task.getId()) == markId){ 
                /*if the id of the task in the ArrayList matches with 
                the id of the task to updated, the description of the task is
                set to newDesc and the update time is also updated*/
                task.setStatus("done");
            }
            toWrite += """
                    {
                        "###id" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  """

            ]"""; // adds the closing bracket ]
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
        System.out.println("Description: " + task.getDescription());
        System.out.println("Created: " + TimeFormatting.formatLocalMedium(Instant.parse(task.getTimeCreated())));
        System.out.println("Last Updated: " + TimeFormatting.formatLocalMedium(Instant.parse(task.getTimeUpdated())));
        System.out.println("Status: " + task.getStatus());
    }

    //displays all tasks
    public static void displayTasks(){
        List<Task> tasks = JsonRead.read(); // calls the read function of JsonRead class to get the ArrayList of all Task Objects
        int i = 0;
        for (Task task: tasks){
            i++;
            if (task.getStatus().equals("deleted")){ 
                /* if the status of the current task is "deleted", 
                the task is displayed as "deleted", 
                and the loop moves on to the next iteration*/ 
                System.out.println("— — — — — TASK %d — — — — —".formatted(i));
                System.out.println("\u001B[3mdeleted\u001B[23m");
                continue;
            }
        System.out.println("— — — — — TASK %d — — — — —".formatted(i)); // displayTask for a task will only be called when its status is not "deleted"
        displayTask(task);
        }
    }
}

class TimeFormatting{
    public static String formatLocalMedium(Instant now){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                            .withLocale(Locale.getDefault())
                                            .withZone(ZoneId.systemDefault());
        return dateTimeFormatter.format(now);
    }
}