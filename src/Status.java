import java.util.*;

/**
 * Status provides object to manage status of a task. It has default value of
 * "to do".
 * 
 * @author aaronsum
 * @version 1.0, 2018-03-05
 */
public class Status {
	public static final List<String> STATUSES = Arrays.asList("to do", "in progress", "done", "overdue");
	private String status;

	/**
	 * Construct object with default value of "to do".
	 */
	public Status() {
		status = "to do";
	}

	/**
	 * Provide status the object.
	 * 
	 * @return Status of the object.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Assess if the specify element is a valid status.
	 * 
	 * @param status
	 *            Element to be assessed.
	 * @return true if the specified element is a valid status.
	 */
	public boolean isValidStatus(String status) {
		if (STATUSES.contains(status.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Modify the status with the specify element if it is a valid status and
	 * different from existing regardless of its letter case.
	 * 
	 * @param newStatus
	 *            Element for modification of the existing status.
	 * @return true if status was modified.
	 */
	public boolean setStatus(String newStatus) {
		newStatus = newStatus.toLowerCase();
		if (isValidStatus(newStatus) && !newStatus.equals(status)) {
			status = newStatus;
			return true;
		} else {
			System.out.println("Invalid status given.");
			return false;
		}

	}
}
