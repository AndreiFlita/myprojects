package GraphicalUserInterface;

import BusinessLogic.TasksManagement;
import DataModel.Employee;
import DataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ShowEmployees {
    public static void showEmployeesInterface() {
        JFrame frame = new JFrame("Employees List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        List<Employee> employees = TasksManagement.getAllEmployees();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(employees.size(), 1, 10, 10));

        for (Employee employee : employees) {
            JPanel employeePanel = new JPanel(new BorderLayout());
            JLabel employeeLabel = new JLabel("ID: " + employee.getIdEmployee() + " - Name: " + employee.getName());

            JComboBox<Task> taskDropdown = new JComboBox<>();
            List<Task> tasks = TasksManagement.employeeTasks.getOrDefault(employee, Collections.emptyList());
            for (Task task : tasks) {
                taskDropdown.addItem(task);
            }

            JButton modifyStatusButton = new JButton("Modify Task Status");
            modifyStatusButton.addActionListener(e -> {
                Task selectedTask = (Task) taskDropdown.getSelectedItem();
                if (selectedTask != null) {
                    TasksManagement.modifyTaskStatus(employee.getIdEmployee(), selectedTask.getIdTask());
                    JOptionPane.showMessageDialog(frame, "Task status updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No task selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton calculateWorkDurationButton = new JButton("Calculate Work Duration");
            calculateWorkDurationButton.addActionListener(e -> {
                int totalDuration = TasksManagement.calculateEmployeeWorkDuration(employee.getIdEmployee());
                JOptionPane.showMessageDialog(frame, "Total Work Duration: " + totalDuration + " hours", "Work Duration", JOptionPane.INFORMATION_MESSAGE);
            });

            JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            taskPanel.add(new JLabel("Tasks: "));
            taskPanel.add(taskDropdown);
            taskPanel.add(modifyStatusButton);
            taskPanel.add(calculateWorkDurationButton);

            employeePanel.add(employeeLabel, BorderLayout.NORTH);
            employeePanel.add(taskPanel, BorderLayout.CENTER);
            panel.add(employeePanel);
        }

        JLabel titleLabel = new JLabel("Employees List", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backButton);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}




