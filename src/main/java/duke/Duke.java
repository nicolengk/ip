package duke;

import duke.task.Task;
import duke.utils.Parser;
import duke.utils.Storage;
import duke.utils.TaskList;
import duke.utils.Ui;

import java.util.Scanner;

public class Duke {
    private Scanner scan = new Scanner(System.in);
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private TaskList tasks = new TaskList();

    public void run () {
        //load tasks from file
        this.storage.loadTasksFromFile(tasks.getTasks());
        //greeting
        this.ui.printGreeting();
        //processing user commands
        while (true) {
            //read user input
            String userInput = scan.nextLine();
            Parser parser = new Parser(userInput);
            //check for exit command first
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }

            try {
                String userCommand = parser.inputCommand();
                if (userCommand.equals("LIST")) {
                    this.ui.printList(tasks.getTasks());
                } else if (userCommand.equals("MARK")) {
                    Task task = this.tasks.markTaskDone(parser.getTaskNumber());
                    System.out.println("Nice! I've marked this task as done:\n" + task);
                } else if (userCommand.equals("UNMARK")) {
                    Task task = this.tasks.unmarkTask(parser.getTaskNumber());
                    System.out.println("OK, I've marked this task as not done yet:\n" + task);
                } else if (userCommand.equals("TODO")) {
                    Task task = this.tasks.addTodoTask(parser.getTodoDescription());
                    System.out.println("Got it. I've added this task:\n " + task);
                    System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
                } else if (userCommand.equals("DEADLINE")) {
                    String[] descriptionAndDateTime = parser.getDeadlineDescription();
                    Task task = this.tasks.addDeadlineTask(descriptionAndDateTime[0], parser.getDateTime(descriptionAndDateTime[1]));
                    System.out.println("Got it. I've added this task:\n " + task);
                    System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
                } else if (userCommand.equals("EVENT")) {
                    String[] deadlineDescription = parser.getEventDescription();
                    Task task = this.tasks.addEventTask(deadlineDescription[0], parser.getDateTime(deadlineDescription[1]),
                        parser.getDateTime(deadlineDescription[2]));
                    System.out.println("Got it. I've added this task:\n " + task);
                    System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
                } else if (userCommand.equals("DELETE")) {
                    Task task = this.tasks.deleteTask(parser.getTaskNumber());
                    System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
                    this.ui.printList(tasks.getTasks());
                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
                this.storage.saveTasksToFile(this.tasks.getTasks());
            } catch (DukeException e) {
                System.out.println("☹ OOPS!!! " + e.getMessage());
            }
        }
        this.ui.printFarewell();
    }
    public static void main(String[] args) {
        new Duke().run();
    }
}