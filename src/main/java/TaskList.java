import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class TaskList {
    private List<Task> toDoList;

    public TaskList(List<Task> toDoList) {
        this.toDoList = toDoList;
    }
    public TaskList() {
        this.toDoList = new ArrayList<>();
    }
    public List<Task> getTasks() {
        return this.toDoList;
    }

    public int getSize() {
        return this.toDoList.size();
    }

    public Task markTaskDone(int taskIndex) throws DukeException {
        try {
            Task task = toDoList.get(taskIndex);
            task.markDone();
            return task;
        } catch (NullPointerException e) {
            throw new DukeException("invalid task number");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("invalid task number");
        }
    }
    public Task unmarkTask(int taskIndex) throws DukeException {
        try {
            Task task = toDoList.get(taskIndex);
            task.markNotDone();
            return task;
        } catch (NullPointerException e) {
            throw new DukeException("invalid task number");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("invalid task number");
        }
    }

    public Task addTodoTask(String todoDescription) throws DukeException {
        Task newTask = new Todo(todoDescription);
        toDoList.add(newTask);
        return newTask;
    }
    public Task addDeadlineTask(String description, LocalDateTime dateTime) throws DukeException {
        Task newTask = new Deadline(description, dateTime);
        toDoList.add(newTask);
        return newTask;
    }
    public Task addEventTask(String description, LocalDateTime fromDatetime, LocalDateTime toDateTime) throws DukeException {
        Task newTask = new Event(description, fromDatetime, toDateTime);
        toDoList.add(newTask);
        return newTask;
    }
    public Task deleteTask(int taskNumber) throws DukeException {
        try {
            Task task = toDoList.get(taskNumber - 1);
            toDoList.remove(taskNumber - 1);
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("invalid task number");
        } catch (NullPointerException e) {
            throw new DukeException("invalid task number");
        }

    }
}
