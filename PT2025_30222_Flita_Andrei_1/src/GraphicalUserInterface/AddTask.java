package GraphicalUserInterface;

import DataAccess.SerializationUtil;
import DataModel.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddTask {

    public static List<Task> createdTasks = SerializationUtil.loadCreatedTasks();
    static List<Task> complexTasks = SerializationUtil.loadComplexTasks();


    public static void AddTaskInterface() {

        JFrame frame = new JFrame("Add Task");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel nameLabel = new JLabel("Task Name:");
        JTextField nameField = new JTextField();
        JLabel startHourLabel = new JLabel("Start Hour:");
        JTextField startHourField = new JTextField();
        JLabel endHourLabel = new JLabel("End Hour:");
        JTextField endHourField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startHourLabel);
        inputPanel.add(startHourField);
        inputPanel.add(endHourLabel);
        inputPanel.add(endHourField);

        DefaultListModel<String> complexTaskListModel = new DefaultListModel<>();
        JList<String> complexTaskList = new JList<>(complexTaskListModel);
        JScrollPane complexTaskScrollPane = new JScrollPane(complexTaskList);
        updateComplexTaskList(complexTaskListModel);

        JButton addSimpleTaskButton = new JButton("Add Simple Task");
        JButton addComplexTaskButton = new JButton("Add Complex Task");
        JButton addSimpleToComplexButton = new JButton("Add Simple Task to Complex Task");
        JButton addComplexToComplexButton = new JButton("Add Complex Task to Complex Task");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(addSimpleTaskButton);
        buttonPanel.add(addComplexTaskButton);
        buttonPanel.add(addSimpleToComplexButton);
        buttonPanel.add(addComplexToComplexButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(complexTaskScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addSimpleTaskButton.addActionListener(e -> {
            String name = nameField.getText();
            int startHour = Integer.parseInt(startHourField.getText());
            int endHour = Integer.parseInt(endHourField.getText());
            SimpleTask task = new SimpleTask("Pending", name, startHour, endHour);
            createdTasks.add(task);
            SerializationUtil.saveCreatedTasks(createdTasks);
            SerializationUtil.saveCounterData();
            JOptionPane.showMessageDialog(frame, "Simple Task Added!");
        });

        addComplexTaskButton.addActionListener(e -> {
            String name = nameField.getText();
            ComplexTask task = new ComplexTask("Pending", name);
            createdTasks.add(task);
            complexTasks.add(task);
            updateComplexTaskList(complexTaskListModel);
            SerializationUtil.saveCreatedTasks(createdTasks);
            SerializationUtil.saveComplexTasks(complexTasks);
            SerializationUtil.saveCounterData();
            JOptionPane.showMessageDialog(frame, "Complex Task Added!");
        });

        addSimpleToComplexButton.addActionListener(e -> {
            String selectedComplexName = complexTaskList.getSelectedValue();
            ComplexTask selectedComplex = findComplexTaskByName(selectedComplexName);

            if (selectedComplex != null) {
                String name = nameField.getText();
                int startHour = Integer.parseInt(startHourField.getText());
                int endHour = Integer.parseInt(endHourField.getText());
                SimpleTask simpleTask = new SimpleTask("Pending", name, startHour, endHour);
                selectedComplex.addTask(simpleTask);
                SerializationUtil.saveCreatedTasks(createdTasks);
                SerializationUtil.saveCounterData();
                JOptionPane.showMessageDialog(frame, "Simple Task Added to Complex Task!");
            } else {
                JOptionPane.showMessageDialog(frame, "Select a Complex Task first!");
            }
        });

        addComplexToComplexButton.addActionListener(e -> {
            String selectedComplexName = complexTaskList.getSelectedValue();
            ComplexTask selectedComplex = findComplexTaskByName(selectedComplexName);

            if (selectedComplex != null) {
                String name = nameField.getText();
                ComplexTask newComplexTask = new ComplexTask("Pending", name);
                selectedComplex.addTask(newComplexTask);
                complexTasks.add(newComplexTask);
                updateComplexTaskList(complexTaskListModel);
                SerializationUtil.saveCreatedTasks(createdTasks);
                SerializationUtil.saveComplexTasks(complexTasks);
                SerializationUtil.saveCounterData();
                JOptionPane.showMessageDialog(frame, "Complex Task Added to Complex Task!");
            } else {
                JOptionPane.showMessageDialog(frame, "Select a Complex Task first!");
            }
        });

        frame.setVisible(true);
    }

    private static void updateComplexTaskList(DefaultListModel<String> listModel) {
        listModel.clear();
        for (Task task : complexTasks) {
                listModel.addElement(task.getNameTask());
        }
    }

    private static ComplexTask findComplexTaskByName(String name) {
        for (Task task : complexTasks) {
            if (task instanceof ComplexTask && task.getNameTask().equals(name)) {
                return (ComplexTask) task;
            }
        }
        return null;
    }
}





