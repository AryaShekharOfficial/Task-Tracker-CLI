import java.util.ArrayList;
import java.util.List;


public class TasksParser{
    public static List<Task> handleparse(String jsonFile){
        List<Task> taskList = new ArrayList<>();
        int i = 0; //index
        int d = 0; //depth
        int s = -1; //start
        for(i = 0; i < jsonFile.length(); i++){ //The loop runs for the entire length of the extracted Json
            char c = jsonFile.charAt(i); //examines character by character
            if (c == '{'){ //if '{' is discovered d is incremented by 1
                if (d == 0){ //if d == 0, which would mean it is outside a task body and has encountered the starting { for another, start 's' would be set to index 'i'
                    s = i;
                }
                d++;
            }
            else if(c == '}'){ //if } is encountered d is decremented by 1
                d--;
                if ( d == 0 ){ // d == 0 would mean the loop has reached the closing } of a Task body
                    String individualTask = jsonFile.substring(s, i+1); //individualTask would contain the task substring
                    taskList.add(IndividualTaskParser.individualTaskParse(individualTask)); //individualTaskParse takes the task substring converts it into a Task object and then it gets added to the list
                }
            }
        }
        return taskList; //the ArrayList, containing all Tasks as objects from the json file, is returned to JsonRead
    }
}

class IndividualTaskParser{
    public static Task individualTaskParse(String individualTask){
        int i = 0; //for looping through individualTask string
        String value = ""; //will temporarily contain the parsed property values 
        String constructTask[] = new String[6]; //will hold all the property values 
        int taskIndex = 0; //will help to assign values to the constructTask array
        for (i = 0; i < individualTask.length(); i++){ //runs for the entire length of the individualTask string
            if (individualTask.charAt(i) == '#' && individualTask.charAt(i+1) == '#' && individualTask.charAt(i+2) == '#'){ //if ### is detected
                i = i + 3; //jumps to the first character of the key
                while(individualTask.charAt(i) != '"'){ //iterates till the closing " of key is encountered
                    i++;
                }
                i = i + 5; //jumps to the first character of the value
                while(!(individualTask.charAt(i) == '#' && individualTask.charAt(i+1) == '#' && individualTask.charAt(i+2) == '#')){ //iterates till ### is detected
                    value = value + individualTask.charAt(i); //value is constructed till ### is detected
                    i++;
                }
                constructTask[taskIndex] = value; //value is assigned to its corresponding index with the help of taskIndex
                taskIndex++;
                value = ""; // value is reset to hold the next value
            }
        }
        Task indTaskObj = new Task(constructTask[0], constructTask[1], constructTask[2], constructTask[3], constructTask[4], constructTask[5]); // each value is passed to its respective property 
        return indTaskObj; //returns the task object to TaskParser
    }
}