package GraphicalUserInterface;

import BusinessLogic.TasksManagement;
import DataModel.Employee;
import DataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignTask {
    public static void AssignTaskInterface() {
        JFrame frame = new JFrame("Assign Task");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel selectionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        DefaultListModel<String> employeeListModel = new DefaultListModel<>();
        JList<String> employeeList = new JList<>(employeeListModel);
        DefaultListModel<String> taskListModel = new DefaultListModel<>();
        JList<String> taskList = new JList<>(taskListModel);


        List<Employee> employees = TasksManagement.getAllEmployees();
        for (Employee e : employees) {
            employeeListModel.addElement(e.getIdEmployee() + " - " + e.getName());
        }

        for (Task t : AddTask.createdTasks) {
                taskListModel.addElement(t.getIdTask() + " - " + t.getNameTask());
        }

        selectionPanel.add(new JScrollPane(employeeList));
        selectionPanel.add(new JScrollPane(taskList));

        JButton assignButton = new JButton("Assign Task");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployee = employeeList.getSelectedValue();
                String selectedTask = taskList.getSelectedValue();

                if (selectedEmployee != null && selectedTask != null) {
                    int employeeId = Integer.parseInt(selectedEmployee.split(" - ")[0]);
                    int taskId = Integer.parseInt(selectedTask.split(" - ")[0]);

                    Task taskToAssign = null;
                    for (Task t : AddTask.createdTasks) {
                        if (t.getIdTask() == taskId) {
                            taskToAssign = t;
                            break;
                        }
                    }

                    if (taskToAssign != null) {
                        TasksManagement.assignTaskToEmployee(employeeId, taskToAssign);
                        taskListModel.removeElement(selectedTask);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an employee and a task.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(selectionPanel, BorderLayout.CENTER);
        frame.add(assignButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

