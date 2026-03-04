package DataModel;

import java.io.Serial;
import java.util.*;

public non-sealed class ComplexTask extends Task {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Task> subTasks = new ArrayList<>();

    public ComplexTask(String status, String name) {
        super(name, status);
    }

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    @Override
    public int estimateDuration() {
        int totalDuration = 0;
        for (Task task : subTasks) {
            totalDuration += task.estimateDuration();
        }
        return totalDuration;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }
}
