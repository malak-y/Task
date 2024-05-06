package Service;

import java.util.HashMap;
import java.util.Map;

import Model.Sprint;
import Model.SprintReport;
import Model.Status;
import Model.Task;

public class SprintService {
    private Map<Integer, Sprint> sprints;
    private int currentSprintId;

    public SprintService() {
        this.sprints = new HashMap<>();
        this.currentSprintId = 0;
    }

    public Sprint startNewSprint(String name) {
        currentSprintId++;
        Sprint sprint = new Sprint();
        sprints.put(currentSprintId, sprint);
        return sprint;
    }

    public Sprint endSprint(int sprintId) {
        Sprint sprint = sprints.get(sprintId);
        startNewSprint("Sprint " + (currentSprintId + 1));
        return sprint;
    }

    public Sprint getSprintById(int sprintId) {
        return sprints.get(sprintId);
    }

    public SprintReport generateSprintReport(int sprintId) {
        Sprint sprint = sprints.get(sprintId);
        Map<Status, Integer> taskCountByStatus = new HashMap<>();
        int totalCompletedStoryPoints = 0;
        int totalUncompletedStoryPoints = 0;

        for (Task task : sprint.getTasks()) {
            switch (task.getStatus()) {
                case DONE:
                    totalCompletedStoryPoints += task.getStoryPoints();
                    break;
                default:
                    totalUncompletedStoryPoints += task.getStoryPoints();
                    break;
            }
            taskCountByStatus.put(task.getStatus(), taskCountByStatus.getOrDefault(task.getStatus(), 0) + 1);
        }

        return new SprintReport(sprintId, taskCountByStatus, totalCompletedStoryPoints, totalUncompletedStoryPoints);
    }

   
}
