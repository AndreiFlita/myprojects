package GraphicalUserInterface;

import DataModel.ComplexTask;
import DataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static GraphicalUserInterface.AddTask.createdTasks;

public class ShowTasks {
    public static void showTaskInterface() {
        JFrame frame = new JFrame("Task List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 4, 10, 10));

        DefaultListModel<String> model1 = new DefaultListModel<>();
        DefaultListModel<String> model2 = new DefaultListModel<>();
        DefaultListModel<String> model3 = new DefaultListModel<>();
        DefaultListModel<String> model4 = new DefaultListModel<>();

        JList<String> list1 = new JList<>(model1);
        JList<String> list2 = new JList<>(model2);
        JList<String> list3 = new JList<>(model3);
        JList<String> list4 = new JList<>(model4);

        JScrollPane scroll1 = new JScrollPane(list1);
        JScrollPane scroll2 = new JScrollPane(list2);
        JScrollPane scroll3 = new JScrollPane(list3);
        JScrollPane scroll4 = new JScrollPane(list4);

        for (Task task : createdTasks) {
            model1.addElement(task.getNameTask() + " (" + task.estimateDuration() + ")");
        }

        list1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSubTaskList(list1, model2, createdTasks);
                model3.clear();
                model4.clear();
            }
        });

        list2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSubTaskList(list2, model3, getSelectedTasks(list1));
                model4.clear();
            }
        });

        list3.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSubTaskList(list3, model4, getSelectedTasks(list2));
            }
        });

        frame.add(scroll1);
        frame.add(scroll2);
        frame.add(scroll3);
        frame.add(scroll4);

        frame.setVisible(true);
    }

    private static void updateSubTaskList(JList<String> list, DefaultListModel<String> model, List<Task> tasks) {
        model.clear();
        if (!list.isSelectionEmpty()) {
            String selectedTask = list.getSelectedValue().split(" \\(")[0];
            for (Task task : tasks) {
                if (task.getNameTask().equals(selectedTask) && task instanceof ComplexTask) {
                    for (Task subTask : ((ComplexTask) task).getSubTasks()) {
                        model.addElement(subTask.getNameTask() + " (" + subTask.estimateDuration() + ")");
                    }
                }
            }
        }
    }

    private static List<Task> getSelectedTasks(JList<String> list) {
        if (list.isSelectionEmpty()) {
            return List.of();
        }
        String selectedTask = list.getSelectedValue().split(" \\(")[0];
        return findSubTasks(selectedTask, createdTasks);
    }

    private static List<Task> findSubTasks(String taskName, List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getNameTask().equals(taskName) && task instanceof ComplexTask) {
                return ((ComplexTask) task).getSubTasks();
            } else if (task instanceof ComplexTask) {
                List<Task> subTasks = findSubTasks(taskName, ((ComplexTask) task).getSubTasks());
                if (!subTasks.isEmpty()) {
                    return subTasks;
                }
            }
        }
        return List.of();
    }
}
