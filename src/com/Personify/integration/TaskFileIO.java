package com.Personify.integration;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class TaskFileIO extends FileIO{
	private final FilePath TASK_FILE_PATH;
	private static final int NO_OF_PARAMETERS_FOR_EACH_TASK = 4;
	private final List<TaskInfo> tasksData;
	
	public TaskFileIO () {
		tasksData = new ArrayList<>();
		TASK_FILE_PATH = new FilePath("src/data", "tasksData.csv");
	}
	
	public List<TaskInfo> getTasksData() {
		return tasksData;
	}
	
	private List<String> tokenizeTasksDetails(List<String> tasks) {
		int arraySize = NO_OF_PARAMETERS_FOR_EACH_TASK * tasks.size();
		List<String> tasksInfo= new ArrayList<>(arraySize);
		for (String task: tasks) {
			Scanner scanner = new Scanner(task);
			scanner.useDelimiter(";");
			while (scanner.hasNext()) {
				tasksInfo.add(scanner.next());
			}
			scanner.close();
		}
		return tasksInfo;
	}
	
	private void readTaskToTaskInfoCollection() throws IOException {
		String taskName;
		String dueDate;
		String status;
        String priority;
		List<String> eachTask = readEachLineOfFile(TASK_FILE_PATH.getPathToFile());
		List<String> tokenizeTaskData = tokenizeTasksDetails(eachTask);
		Iterator<String> it = tokenizeTaskData.iterator();
		while (it.hasNext()) {
			taskName = it.next();
			dueDate = it.next();
			status = it.next();
			priority = it.next();
			TaskInfo taskInfo = new TaskInfo(taskName, dueDate, status, priority);
			tasksData.add(taskInfo);
		}	
	}
	
	private boolean isTaskDataFileExistsAndReadable() {
		return Files.isReadable(TASK_FILE_PATH.getPathToFile());
	}
	
	public void readTasksDataFromDataFile() throws IOException {
		if (isTaskDataFileExistsAndReadable()) {
			readTaskToTaskInfoCollection();
		}
	}
		
	private void writeTasksToFile(List<String> tasksToWriteToFile) {
		Charset charset = Charset.forName("UTF-8");
		try (BufferedWriter writer = Files.newBufferedWriter(TASK_FILE_PATH.getPathToFile(), charset)) {
			for (String taskInfo : tasksToWriteToFile) {
				writer.write(taskInfo);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		
	}
	
	private List<String> formattingTaskDataForArchive (List<TaskInfo> tasksToArchive) {
		return tasksToArchive.stream()
							.map(TaskInfo::toString)
							.collect(Collectors.toList());
	}
	
	public void archiveTasks(List<TaskInfo> tasksToArchive) {
		List<String> tasksToWriteToFile = formattingTaskDataForArchive(tasksToArchive);
		writeTasksToFile(tasksToWriteToFile);
	}
}
