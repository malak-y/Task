package Model;

import java.util.Map;

public class SprintReport {
    private int sprintId;
    private Map<Status, Integer> taskCountByStatus;
    private int totalCompletedStoryPoints;
    private int totalUncompletedStoryPoints;

    public SprintReport() {
        // Default constructor
    }

    public SprintReport(int sprintId, Map<Status, Integer> taskCountByStatus, int totalCompletedStoryPoints, int totalUncompletedStoryPoints) {
        this.sprintId = sprintId;
        this.taskCountByStatus = taskCountByStatus;
        this.totalCompletedStoryPoints = totalCompletedStoryPoints;
        this.totalUncompletedStoryPoints = totalUncompletedStoryPoints;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public Map<Status, Integer> getTaskCountByStatus() {
        return taskCountByStatus;
    }

    public void setTaskCountByStatus(Map<Status, Integer> taskCountByStatus) {
        this.taskCountByStatus = taskCountByStatus;
    }

    public int getTotalCompletedStoryPoints() {
        return totalCompletedStoryPoints;
    }

    public void setTotalCompletedStoryPoints(int totalCompletedStoryPoints) {
        this.totalCompletedStoryPoints = totalCompletedStoryPoints;
    }

    public int getTotalUncompletedStoryPoints() {
        return totalUncompletedStoryPoints;
    }

    public void setTotalUncompletedStoryPoints(int totalUncompletedStoryPoints) {
        this.totalUncompletedStoryPoints = totalUncompletedStoryPoints;
    }
}
