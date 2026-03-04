package GraphicalUserInterface;

import javax.swing.*;
import java.awt.*;

public class InterfaceGUI {
    public static void MainInterfacePage() {
        JFrame frame = new JFrame("Manager Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addEmployeeButton = new JButton("Add Employee");
        JButton addTaskButton = new JButton("Add Task");
        JButton assignTaskButton = new JButton("Assign Task");
        JButton showEmployeesButton = new JButton("Show Employees");
        JButton showTasksButton = new JButton("Show Tasks");


        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(assignTaskButton);
        buttonPanel.add(showEmployeesButton);
        buttonPanel.add(showTasksButton);



        JLabel titleLabel = new JLabel("Manager Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        addEmployeeButton.addActionListener(e -> {AddEmployee.AddEmployeeInterface();});
        showEmployeesButton.addActionListener(e -> {ShowEmployees.showEmployeesInterface();});
        addTaskButton.addActionListener(e -> {AddTask.AddTaskInterface();});
        showTasksButton.addActionListener(e -> {ShowTasks.showTaskInterface();});
        assignTaskButton.addActionListener(e -> {AssignTask.AssignTaskInterface();});


    }
}
