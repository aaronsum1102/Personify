

import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.base.Motivation;
import com.Personify.base.Task;
import com.Personify.base.TaskCollection;
import com.Personify.integration.TaskInfo;

class TaskCollectionTest {
	private TaskCollection holder;
	private Messenger taskMessages;
	private Motivation motivationalQuotes;
	private TaskInfo taskInfo;
	private Task task;

	@BeforeEach
	void setUp() throws Exception {
		motivationalQuotes = new Motivation();
		taskMessages = new Messenger();
		holder = new TaskCollection(taskMessages, motivationalQuotes);
		taskInfo = new TaskInfo("laundry", "2018-03-10", "in progress", "medium");
		task = new Task(taskInfo, taskMessages, motivationalQuotes);
		holder.getAddTaskSummary(task);
	}

	@AfterEach
	void tearDown() {
		holder = null;
		taskMessages = null;
	}

	@Test
	void testAddTask() throws DateTimeParseException {
		taskInfo = new TaskInfo("shopping", "", "", "");
		Task task = new Task(taskInfo, taskMessages, motivationalQuotes);
		boolean result = holder.getAddTaskSummary(task);
		assertEquals(true, result);
	}

	@Test
	void testAddTaskWithEmptyTaskName() throws DateTimeParseException {
		taskInfo = new TaskInfo("", "", "", "");
		Task task = new Task(taskInfo, taskMessages, motivationalQuotes);
		boolean result = holder.getAddTaskSummary(task);
		assertEquals(false, result);
	}
	
	@Test
	void testAddTaskWithRepeatedTaskName() throws DateTimeParseException {
		taskInfo = new TaskInfo("laundry", "", "", "");
		Task task = new Task(taskInfo, taskMessages, motivationalQuotes);
		taskMessages.clearMessages();
		boolean result = holder.getAddTaskSummary(task);
		assertEquals(false, result);
		String expectedMessage = "You have the same task in record. Please give me a new name.";
		boolean actualResult = taskMessages.getMessages().contains(expectedMessage);
		assertEquals(true, actualResult);
	}
	
	@Test
	void testGetTaskSortedByName() {
		TaskInfo newTaskInfo = new TaskInfo("baking", "2018-03-15", "in progress", "low");
		Task newTask = new Task(newTaskInfo, taskMessages, motivationalQuotes);
		holder.getAddTaskSummary(newTask);
		holder.getTasksSortedByName();
		String expectedResult = "1. " + newTask.toString();
		assertEquals(true, taskMessages.getMessages().contains(expectedResult));	
	}
	
	@Test
	void testEditFileName() {
		String newName = "shopping";
		int index = 1;
		holder.editTaskName(index, newName);
		assertEquals(newName, holder.getTasks().get(index - 1).getName());
	}
	
	@Test
	void testEditDueDate() {
		String newDueDate = "2018-03-20";
		int index = 1;
		holder.editDueDate(index, newDueDate);
		assertEquals(newDueDate, holder.getTasks().get(index - 1).getDueDate().toString());
	}
	
	@Test
	void testEditStatus() {
		String newStatus = "done";
		int index = 1;
		holder.editStatus(index, newStatus);
		assertEquals(newStatus, holder.getTasks().get(index - 1).getStatusObject().getStatus());
	}
	
	@Test
	void testEditPriority() {
		String newPriority = "high";
		int index = 1;
		holder.editPriority(index, newPriority);
		assertEquals(newPriority, holder.getTasks().get(index - 1).getPrioirtyObject().getPriority());
	}
}
