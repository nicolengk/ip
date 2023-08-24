public class Task {
    private String description;
    private Boolean isDone;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }
    public void markNotDone() {
        this.isDone = false;
    }
    @Override
    public String toString() {
        return "[" + (isDone? "X" : " ") + "] " + this.description;
    }
}
