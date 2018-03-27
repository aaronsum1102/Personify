package com.Personify.integration;

public class TaskTableColumnName {
    String column1 = "Task Name";
    String column2 = "Due Date";
    String column3 = "Status";
    String column4 = "Priority";
    String column5 = "Details/Collaborators";
    String column6 = "Remarks";

    @Override
    public String toString() {
        return String.format("%3s%-30s%-15s%-15s%-15s%-30s%-30s", " ",
                column1, column2, column3, column4, column5, column6);
    }
}
