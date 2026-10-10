public class Task {
    private int id;
    private String name;
    private String description;
    private String timeCreated;
    private String timeUpdated;
    private String status;

    public Task(int id, String name, String description, String timeCreated, String timeUpdated, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public String getStatus() {
        return status;
    }    

}
 