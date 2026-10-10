import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonReadWrite {
    public static void main(String args[]){
        int choice = 1;
        if (choice == 0){
            JsonWrite jw = new JsonWrite();
            jw.write();
        }
        else{
            JsonRead jr = new JsonRead();
            jr.read();
        }
        
    }
    
}

//To write to a Json file
class JsonWrite{
    public void write(){
        Path filePath = Paths.get("tasks.json");
        String toWrite = """
                {
                    "###id" : "1###",
                    "###name" : "My Task###",
                    "###description" : "bla bla bla###",
                    "###timeCreated" : "sth###",
                    "###timeUpdated" : "sth###",
                    "###status" : "sth###"
                }
                """;
        try{
            Files.writeString(filePath, toWrite);
            System.out.println("Json Written Successfully!");
        } catch (Exception e){
            System.out.println("Error Writing File: " + e);
        }
    }
}

//To Read from a Json file
class JsonRead{
    public void read(){
        Path filepath = Paths.get("tasks.json");
        List<Task> tasks = new ArrayList<>();
        try{
            String readFile = Files.readString(filepath);
            tasks = TasksParser.handleparse(readFile);
            for (Task t: tasks){
                System.out.println(t);
            }
        } catch (Exception e) {
            System.out.println("Error Reading File: " + e);
        }
    }
}