import java.time.LocalDate;

/**
 * Task provides an object for managing a task. Task can be provided and each
 * task will hold information about the task name, due date, priority and
 * status. There is also a reminder and motivational message provided in a task
 * object depending on each situation.
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-03
 *
 */
public class Task {
	private String name;
	private LocalDate dueDate;
	private Status status;
	private String priority;
	private Reminder reminder;
	private Motivation motivation;

	/**
	 * Construct an task object with the specify elements. If the specified value is
	 * invalid, the object will construct with default values.
	 * 
	 * @param name
	 *            Task name of the object.
	 * @param dueDate
	 *            Due date of the task, default value is the date where the task is
	 *            created.
	 * @param status
	 *            Status of the task, default status is "to do".
	 * @param priority
	 *            Priority of the task, default priority is "high".
	 */
	public Task(String name, String dueDate, String status, String priority) {
		this.name = name;
		this.dueDate = isDateFormatValid(dueDate) ? LocalDate.parse(dueDate) : LocalDate.now();
		this.status = new Status();
		this.status.setStatus(status);
		this.priority = priority;
		reminder = new Reminder(this.dueDate);
		motivation = new Motivation();
	}

	/**
	 * Helper method to provide sanity check for the date format.
	 * 
	 * @param date
	 *            Element to be assessed.
	 * @return
	 */
	private static boolean isDateFormatValid(String date) {
		if (date.charAt(4) == '-' && date.charAt(7) == '-' && date.length() == 10) {
			return true;
		}
		return false;
	}

	/**
	 * Provide task name.
	 * 
	 * @return Task name of object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Provide due date of the object.
	 * 
	 * @return Due date of object.
	 */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
	 * Modify name of object if the name is unique and is not empty.
	 * 
	 * @param newName
	 *            Element for modification of object's name.
	 * @return true if object's name was changed.
	 */
	public boolean changeName(String newName) {
		if (!(newName.toLowerCase().equals(name.toLowerCase())) && newName.length() != 0) {
			name = newName;
			return true;
		} else {
			System.out.println("You have this task in your list already. Please provide a unique name");
			return false;
		}
	}

	/**
	 * Modify due date of object if the specify element is unique.
	 * 
	 * @param newDueDate
	 *            Element for modification of due date.
	 * @return true if the due date was changed.
	 */
	public boolean changeDueDate(String newDueDate) {
		if (!(dueDate.toString().equals(newDueDate)) && isDateFormatValid(newDueDate)) {
			dueDate = LocalDate.parse(newDueDate);
			return true;
		} else {
			System.out.println("Invalid Date. Due date is unchange.");
			return false;
		}
	}

	/**
	 * Provide a summary of the object information. It also include a reminder and
	 * motivational quotes.
	 */
	public void getSummary() {
		System.out.println("Task name: " + name);
		System.out.println("Due date: " + dueDate);
		System.out.println("Status: " + status.getStatus());
		System.out.println("Task priority: " + priority);
		System.out.println(reminder.getMessage());
		if (reminder.findDaysLeft() > 1) {
			System.out.format("A motivational quote for you: \n%s", motivation.getQuote());
		}
	}
}
