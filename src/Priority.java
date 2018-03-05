import java.util.*;

/**
 * Priority provides object to manage priority status of a task. It has a
 * default priority of "high".
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-05
 */
public class Priority {
	public static final List<String> PRIORITIES = Arrays.asList("low", "medium", "high");
	private String priority;

	/**
	 * Construct object with default priority of "high".
	 */
	public Priority() {
		priority = "high";
	}

	/**
	 * Provide priority status of object.
	 * 
	 * @return Priority status.
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Access if the specified element is one of the allowable priority.
	 * 
	 * @param priority
	 *            Element to be checked.
	 * @return true if the specify element is in default list.
	 */
	public boolean isValidPriority(String priority) {
		if (PRIORITIES.contains(priority.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Modify the priority with the specify element, if it is a valid priority and
	 * is different from existing priority regardless of its letter case.
	 * 
	 * @param newPriority
	 *            Element for modification of the existing priority.
	 * @return true if priority was modified.
	 */
	public boolean setPriority(String newPriority) {
		newPriority = newPriority.toLowerCase();
		if (isValidPriority(newPriority) && !newPriority.equals(priority.toLowerCase())) {
			priority = newPriority;
			return true;
		} else {
			System.out.println("Invalid priority given.");
			return false;
		}

	}
}
