import java.util.ArrayList;
import java.util.List;


public class TasksParser{
    public static void handleparse(String jsonFile){
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
                    System.out.println(individualTask);
                }

            }
        }

    }
}