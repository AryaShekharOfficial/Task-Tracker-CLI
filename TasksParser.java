import java.util.ArrayList;
import java.util.List;


public class TasksParser{
    public static void handleparse(String jsonFile){
        List<Task> taskList = new ArrayList<>();
        int i = 0;
        int d = 0;
        int s = -1;
        for(i = 0; i < jsonFile.length(); i++){
            char c = jsonFile.charAt(i);
            if (c == '{'){
                if (d == 0){
                    s = i;
                }
                d++;
            }
            else if(c == '}'){
                d--;
                if ( d == 0 ){
                    String individualTask = jsonFile.substring(s, i+1);
                    System.out.println(individualTask);
                }

            }
        }

    }
}