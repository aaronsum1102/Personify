package com.Personify.textView;

/**
 * Provide the header format for display tasks details.
 */
class TaskTableColumnName {
    private static final String COLUMN1 = "Task Name";
    private static final String COLUMN2 = "Due Date";
    private static final String COLUMN3 = "Status";
    private static final String COLUMN4 = "Priority";
    private static final String COLUMN5 = "Details/Collaborators";
    private static final String COLUMN6 = "Remarks";

    /**
     * Provide String representation of the header with specific format and column names.
     * @return a String representation of the header.
     */
    @Override
    public String toString() {
        return String.format("%3s%-30s%-15s%-15s%-15s%-30s%-30s", " ",
                COLUMN1, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6);
    }
}
