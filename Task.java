public class Task {
    private int id;
    private String name;
    private String description;
    private String timeCreated;
    private String timeUpdated;
    private String status;

    public Task(String id, String name, String description, String timeCreated, String timeUpdated, String status) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", description=" + description + ", timeCreated=" + timeCreated
                + ", timeUpdated=" + timeUpdated + ", status=" + status + "]";
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public void setStatus(String status) {
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
 