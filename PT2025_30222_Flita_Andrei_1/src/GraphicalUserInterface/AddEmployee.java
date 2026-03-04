package GraphicalUserInterface;

import BusinessLogic.TasksManagement;
import DataModel.Employee;

import javax.swing.*;
import java.awt.*;

public class AddEmployee {
    public static void AddEmployeeInterface() {
        JFrame frame = new JFrame("Add Employee Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel NameLabel = new JLabel("Name:");
        JTextField NameField = new JTextField();

        inputPanel.add(NameLabel);
        inputPanel.add(NameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton addEmployeeButton = new JButton("Add");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(backButton);

        JLabel titleLabel = new JLabel("Add Employee Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        addEmployeeButton.addActionListener(e -> {
            String name = NameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a name!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Employee employee = new Employee(name);
                TasksManagement.addEmployee(employee);
                JOptionPane.showMessageDialog(frame, "Employee " + name + " added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Employee added: " + name + " (ID: " + employee.getIdEmployee() + ")");
                NameField.setText("");
            }
        });

        backButton.addActionListener(e -> {frame.dispose();});

    }
}

