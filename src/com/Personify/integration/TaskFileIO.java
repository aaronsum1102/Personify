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
	private List<TaskInfo> tasksData;
	
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
		String taskName = "";
		String dueDate = "";
		String status = "";
		String priority = "";
		List<String> eachTask = readEachLineOfFile(TASK_FILE_PATH.getPathToFile());
		List<String> tokenizedTaskData = tokenizeTasksDetails(eachTask);
		Iterator<String> it = tokenizedTaskData.iterator();
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
		
	private void writeTasksToFile(List<String> tasksToWriteToFile) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		try (BufferedWriter writter = Files.newBufferedWriter(TASK_FILE_PATH.getPathToFile(), charset)) {
			for (String taskInfo : tasksToWriteToFile) {
				writter.write(taskInfo);
				writter.newLine();
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		
	}
	
	private List<String> formattingTaskDateForArchive (List<TaskInfo> tasksToArchive) {
		return tasksToArchive.stream()
							.map(taskInfo -> taskInfo.toString())
							.collect(Collectors.toList());
	}
	
	public void archiveTasks(List<TaskInfo> tasksToArchive) throws IOException {
		List<String> tasksToWriteToFile = formattingTaskDateForArchive(tasksToArchive);
		writeTasksToFile(tasksToWriteToFile);
	}
}
