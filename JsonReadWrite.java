import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

class JsonWrite{
    public void write(){
        Path filePath = Paths.get("tasks.json");
        String toWrite = """
                {
                    "name" : "Aryashekhar",
                    "age" : 20
                }
                {
                    "name" : "Ujjwal",
                    "age" : 23
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

class JsonRead{
    public void read(){
        Path filepath = Paths.get("tasks.json");
        try{
            String readFile = Files.readString(filepath);
            System.out.println(readFile);
        } catch (Exception e) {
            System.out.println("Error Reading File: " + e);
        }
    }
}