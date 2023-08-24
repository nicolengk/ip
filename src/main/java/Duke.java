import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    //create empty list to store stuff to do
    private static List<Task> toDoList = new ArrayList<>();
    //create scanner to read user inputs
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        //greeting
        System.out.println("Hello! I'm Sara");
        System.out.println("What can I do for you?");

        //processing user commands
        while (true) {
            //read user input
            String userInput = scan.nextLine();

            //check for exit command first
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            try {
                processInput(userInput);
            } catch (DukeException e) {
                System.out.println("☹ OOPS!!! " + e.getMessage());
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void processInput(String userInput) throws DukeException {
        if (userInput.equalsIgnoreCase("list")) {
            listTasks();
        } else if (userInput.startsWith("mark")) {
            markTaskDone(userInput);
        } else if (userInput.startsWith("unmark")) {
            unmarkTask(userInput);
        } else if (userInput.startsWith("todo")) {
            addTodoTask(userInput);
        } else if (userInput.startsWith("deadline")) {
            addDeadlineTask(userInput);
        } else if (userInput.startsWith("event")) {
            addEventTask(userInput);
        } else if (userInput.startsWith("delete")) {
            deleteTask(userInput);
        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void listTasks() {
        System.out.println("Here are the tasks in your List:");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println(((i + 1) + ". " + toDoList.get(i)));
        }
    }

    private static void markTaskDone(String userInput) throws DukeException {
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 2) {
            int taskIndex = Integer.parseInt(splitInput[1]) - 1;
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                toDoList.get(taskIndex).markDone();
                System.out.println("Nice! I've marked this task as done:\n" + toDoList.get(taskIndex));
            } else {
                throw new DukeException("invalid task number");
            }
        } else {
            throw new DukeException("task number required");
        }
    }

    private static void unmarkTask(String userInput) throws DukeException {
        String[] splitInput = userInput.split(" ");
        if (splitInput.length == 2) {
            int taskIndex = Integer.parseInt(splitInput[1]) - 1;
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                toDoList.get(taskIndex).markNotDone();
                System.out.println("OK, I've marked this task as not done yet:\n" + toDoList.get(taskIndex));
            } else {
                throw new DukeException("invalid task number");
            }
        } else {
            throw new DukeException("task number required");
        }
    }

    private static void addTodoTask(String userInput) throws DukeException {
        if (userInput.length() <= 5) {
            throw new DukeException("The description of a todo cannot be empty.");
        } else {
            String description = userInput.substring(5).trim();
            Task newTask = new Todo(description);
            toDoList.add(newTask);
            System.out.println("Got it. I've added this task:\n " + newTask);
            System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
        }
    }

    private static void addDeadlineTask(String userInput) throws DukeException {
        if (userInput.length() <= 9) {
            throw new DukeException("The description of a deadline cannot be empty.");
        } else {
            String[] splitInput = userInput.split("/by");
            if (splitInput.length == 2) {
                String description = splitInput[0].substring(9).trim();
                String by = splitInput[1].trim();
                Task newTask = new Deadline(description, by);
                toDoList.add(newTask);
                System.out.println("Got it. I've added this task:\n " + newTask);
                System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
            }
        }
    }

    private static void addEventTask(String userInput) throws DukeException {
        if (userInput.length() <= 6) {
            throw new DukeException("The description of an event cannot be empty.");
        } else {
            String[] splitInput = userInput.split("/from");
            if (splitInput.length == 2) {
                String description = splitInput[0].substring(6).trim();
                String[] eventDetails = splitInput[1].split("/to");
                if (eventDetails.length == 2) {
                    String from = eventDetails[0].trim();
                    String to = eventDetails[1].trim();
                    Task newTask = new Event(description, from, to);
                    toDoList.add(newTask);
                    System.out.println("Got it. I've added this task:\n " + newTask);
                    System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
                }
            }
        }
    }

    private static void deleteTask(String userInput) throws DukeException {
        if (userInput.length() <= 7) {
            throw new DukeException("Please specify what to delete");
        } else {
            String[] splitInput = userInput.split(" ");
            int taskIndex = Integer.parseInt(splitInput[1]) - 1;
            if (taskIndex >= 0 && taskIndex < toDoList.size()) {
                Task removedTask = toDoList.remove(taskIndex);
                System.out.println("Noted. I've removed this task: \n" + removedTask);
                System.out.println("Now you have " + toDoList.size() + " tasks in the list.");
            } else {
                throw new DukeException("invalid task number");
            }
        }
    }
}