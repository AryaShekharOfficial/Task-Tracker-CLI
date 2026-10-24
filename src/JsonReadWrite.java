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



public class JsonReadWrite {
    public static void main(String args[]){
        if (args.length > 0){ // check if there is any element in args for safety
            switch (args[0]){ // implemented switch case for different commands: add, update, delete, list, mark
                case "help":
                    CommandDisplay.displayCommands();
                    break;
                case "add":
                    /*
                        If the case is add, it checks if the number of arguments
                        is less than 2, which would mean the user did not provide any argument
                        (description) after the command. In that case the user will be notified
                        that the command needs an argument.
                        If the number of arguments is greater than 3, that would mean
                        the user probably forgot to wrap the description in double
                        quotes. In that case the user will be notified that the argument needs
                        to be wrapped in double quotes.
                        If not both the above cases, that would mean the user has provided
                        the necessary description, and so the addTask funtion of the JsonWrite class
                        would be called passing args[1] as the argument.
                    */
                    if (args.length < 2){
                        System.out.println("Please provide an argument! eg: task-cli add \"MyTask\"");
                    } else if (args.length > 2){
                        System.out.println("Please enclose the argument in double quotes! eg: task-cli add \"MyTask\"");
                    } else {
                        JsonWrite.addTask(args[1]);
                    }
                    break;
                case "update":
                    if (args.length < 3){
                        System.out.println("Please provide arguments! eg: update 1 \"UpdatedTask\"");
                    } else if(args.length > 3){
                        System.out.println("Please enclose the argument in double quotes! eg: update 1 \"UpdatedTask\"");
                    } else {
                        JsonWrite.updateTask(args[1], args[2]);
                    }
                    break;
                case "delete":
                    if (args.length < 2){
                        System.out.println("Please provide an argument! eg: task-cli delete 1");
                    } else if (args.length > 2){
                        System.out.println("Command \"delete\" accepts only one argument! eg: task-cli delete 1");
                    } else {
                        JsonWrite.deleteTask(args[1]);;
                    }
                    break;
                case "list":
                    int i = 1;
                    if (args.length > 1 && args[1].equals("details")){
                        if (args.length == 2){
                            TaskDisplay.displayTasks();
                            break;
                        }
                        if (args.length > 3){
                            System.out.println("Command \\\"list details\\\" accepts either one argument or no argument! eg: 1) task-cli list details 2) task-cli list details todo 3) task-cli list details in-progress 4) task-cli list details done");
                            break;
                        } else {
                            i = 2;
                        }
                    } else if (args.length > 0){
                        if (args.length == 1){
                            TaskDisplay.displayOnlyTasks("null");
                            break;
                        } else if (args.length > 2){
                            System.out.println("Command \\\"list\\\" accepts either one argument or no argument! eg: 1) task-cli list 2) task-cli list todo 3) task-cli list in-progress 4) task-cli list done");
                            break;
                        }
                    }
                    switch (args[i]){
                        case "todo", "in-progress", "done":
                            if (i == 1){
                                TaskDisplay.displayOnlyTasks(args[i]);
                            } else {
                                TaskDisplay.displayTasksWithStatus(args[i]);
                            }
                            break;
                        default:
                            System.out.println("Invalid command!");
                    }
                    break;
                case "mark-in-progress":
                    if (args.length < 2){
                        System.out.println("Please provide an argument! eg: mark-in-progress 1");
                    } else {
                        //JsonWrite.markTaskAsInProgress(args[1]);
                        JsonWrite.markTask(args[1], "in-progress");
                    }
                    break;
                case "mark-done":
                    if (args.length < 2){
                        System.out.println("Please provide an argument! eg: mark-done 1");
                    } else {
                        //JsonWrite.markTaskAsDone(args[1]);
                        JsonWrite.markTask(args[1], "done");
                    }
                    break;
                default:
                    System.out.println("Invalid Command!");
            }
        } else{
            System.out.println("""
                Welcome To The Task Tracker CLI Tool!
                Use 'task-cli help' to check all the available commands.
            """);
        }
    }
}

//To write to a Json file
class JsonWrite{
    // This function adds the String version of a task to toWrite and returns it
    private static String addTaskToString(Task task){
        String toWrite = """
                    {
                        "###id" : "%s###",
                        "###description" : "%s###",
                        "###timeCreated" : "%s###",
                        "###timeUpdated" : "%s###",
                        "###status" : "%s###"
                    },
                """.formatted(task.getId(), task.getDescription(), task.getTimeCreated(), task.getTimeUpdated(), task.getStatus());
        return toWrite;
    }

    // This function returns an opening [ for other functions to add to the beginning of a String
    private static String addOpeningBracket(){
        String toWrite = """
            [
                """;
        return toWrite;
    }

    // This function returns a closing ] for other functions to add to the end of a String
    private static String addClosingBracket(){
        String toWrite =  """

            ]""";
        return toWrite;
    }
    // This function takes the final toWrite String and writes it to the Json file
    private static void writeToFile(String toWrite){
        Path filePath = Paths.get(FilePath.getFilePath()); // gets the FilePath.getFilePath() file as Path object
        try{
            Files.writeString(filePath, toWrite); // writes the final toWrite string to FilePath.getFilePath()
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }
    // Function to add a task
    public static void addTask(String toAdd){
        String desc = ""; // will be used to hold name and description of the new task
        String toWrite = """
                
                """; // will hold the entire task string to be written to FilePath.getFilePath()
        int prevId; // to store previous id
        desc = toAdd;
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks 
        if (tasks.size() > 0){ // if tasks exist last Id is searched and stored as previous id
            prevId = Integer.parseInt((tasks.get(tasks.size() - 1)).getId());
        }
        else{ // if no tasks exist previous id is set to 0
            prevId = 0;
        }
        // an initial [ is added to toWrite
        toWrite += addOpeningBracket();
        int newId = ++prevId; // the new id will obviously be previoius id + 1
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            toWrite += addTaskToString(task);
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
        writeToFile(toWrite);
        System.out.println("Task Successfully Added! (ID: %d)".formatted(newId));
    }

    // Function to update a task
    public static void updateTask(String updateIdString, String newDesc){
        int updateId = Integer.parseInt(updateIdString);
        String toWrite = """
                
                """; //will hold the entire task string to be written to FilePath.getFilePath()
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        tasks.get(updateId-1).setDescription(newDesc);
        tasks.get(updateId-1).setTimeUpdated(Instant.now().toString());
        // an initial [ is added to toWrite
        toWrite += addOpeningBracket();
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            toWrite += addTaskToString(task);
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite += addClosingBracket(); // adds the closing bracket ]
        writeToFile(toWrite);
        System.out.println("Task Updated Successfully! (ID: %d)".formatted(updateId));
    }

    // Function to delete a task
    public static void deleteTask(String delIdString){
        String toWrite = """
                
                """; //will hold the entire task string to be written to FilePath.getFilePath()
        int delId = Integer.parseInt(delIdString); //will hold the id of the task needs to be deleted
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        tasks.get(delId - 1).setStatus("deleted");
        tasks.get(delId - 1).setDescription(null);
        tasks.get(delId - 1).setTimeCreated(null);
        tasks.get(delId - 1).setTimeUpdated(null);
        // an initial [ is added to toWrite
        toWrite += addOpeningBracket();
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            toWrite += addTaskToString(task);
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  addClosingBracket(); // adds the closing bracket ]
        writeToFile(toWrite);
        System.out.println("Task Deleted Successfully! (ID: %d)".formatted(delId));
    }

    // Function to mark the status of a task either "in-progress" or "done"
    public static void markTask(String markIdString, String markAs){
        String toWrite = """
                
                """; //will hold the entire task string to be written to FilePath.getFilePath()
        int markId = Integer.parseInt(markIdString); //will hold the id of the task needs to be marked
        List<Task> tasks = JsonRead.read(); // call the read function to obtain the ArrayList of tasks
        Task taskToBeMarked = tasks.get(markId -1);
        if (!(taskToBeMarked.getStatus().equals(markAs))){
            taskToBeMarked.setStatus(markAs);
            System.out.println("Task with ID:%d is now marked as %s".formatted(markId, markAs));
        } else {
            System.out.println("The task is already %s".formatted(markAs));
        }
        
        // an initial [ is added to toWrite
        toWrite += addOpeningBracket();
        for (Task task: tasks){ // loops through the list and adds the string version of the Task objects to toWrite, till the last Task
            toWrite += addTaskToString(task);
        }
        toWrite = toWrite.substring(0, toWrite.length() - 2); // to avoid the trailing comma
        toWrite +=  addClosingBracket(); // adds the closing bracket ]
        writeToFile(toWrite);
    }
}

//To Read from a Json file
class JsonRead{
    public static List<Task> read(){
        Path filepath = Paths.get(FilePath.getFilePath());
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

    //displays all tasks with full details
    public static void displayTasks(){
        List<Task> tasks = JsonRead.read(); // calls the read function of JsonRead class to get the ArrayList of all Task Objects
        size_check(tasks);
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

    //displays only tasks
    public static void displayOnlyTasks(String status){
        if (status.equals("null")){
            System.out.println("— — — — — ALL TASKS — — — — —");
        } else if (status.equals("in-progress")){
            System.out.println("— — — — — TASKS IN PROGRESS — — — — —");
        } else if (status.equals("done")){
            System.out.println("— — — — — TASKS DONE — — — — —");
        } else {
            System.out.println("— — — — — TASKS TO DO — — — — —");
        }
        List<Task> tasks = JsonRead.read();
        size_check(tasks);
        int i = 1;
        for (Task task: tasks){
            if(task.getStatus().equals("deleted")){
                continue;
            }
            if(status.equals("null")){
                System.out.println("%d: %s \u001B[3m(Task Id: %d)\u001B[23m".formatted(i, task.getDescription(), Integer.parseInt(task.getId())));
                i++;
            } else if (task.getStatus().equals(status)){
                System.out.println("%d: %s \u001B[3m(Task Id: %d)\u001B[23m".formatted(i, task.getDescription(), Integer.parseInt(task.getId())));
                i++;
            }
        }
        if(i == 1 && !status.equals("null")){
            System.out.println("\u001B[3mNone\u001B[23m");
        }
    }

    // Function to display tasks based on status
    public static void displayTasksWithStatus(String status){
        List<Task> tasks = JsonRead.read(); // calls the read function of JsonRead class to get the ArrayList of all Task Objects
        size_check(tasks);
        int i = 0;
        boolean noTask=true;
        for (Task task: tasks){
            i++;
            if (task.getStatus().equals(status)){
                noTask = false;
                System.out.println("— — — — — TASK %d — — — — —".formatted(i)); 
                displayTask(task);
            }
        }
        if(noTask){
                System.out.println("\u001B[3mNone\u001B[23m");
            }
    }

    private static void size_check(List<Task> tasks){
        if (tasks.size() == 0){
            System.out.println("No tasks found. Use 'task-cli add \"YourTaskDescription\"' to create one.");
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

class FilePath{
    private static final String FILE_PATH = "tasks.json";

    public static String getFilePath() {

        if (Files.notExists(Paths.get(FILE_PATH))){
            try{
                Path filePath = Paths.get(FILE_PATH);
                Files.createFile(filePath);
            } catch (Exception e){
                System.out.println("Failed to create Json!");
            }
        }
        return FILE_PATH;
    }
}

class CommandDisplay{
    public static void displayCommands(){
        System.out.println("""
            Available Commands:
                task-cli add "YourTaskDescription"                      To add new tasks
                task-cli update <IndexOfTheTask> "NewTaskDescription"   To update an existing task
                task-cli delete <IndexOfTheTask>                        To delete a task
                task-cli mark-in-progress <IndexOfTheTask>              To mark a task as in-progress
                task-cli mark-done <IndexOfTheTask>                     To mark a task as done
                task-cli list                                           To list all existing tasks
                task-cli list done                                      To list all the tasks that are done
                task-cli list todo                                      To list all the tasks that are not yet started
                task-cli list in-progress                               To list all the tasks that are in-progress
                task-cli list details                                   To list all the tasks with full details(Also lists the deleted tasks)
                task-cli list details done                              To list all the tasks that are done with full details
                task-cli list details todo                              To list all the tasks that are not yet started with full details
                task-cli list details in-progress                       To list all the tasks that are in-progress with full details
        """);
    }
}