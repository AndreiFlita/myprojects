package DataAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import BusinessLogic.TasksManagement;
import DataModel.Employee;
import DataModel.Task;

public class SerializationUtil {

    private static final String FILE_NAME = "tasks_management_data.ser";
    private static final String COUNTER_FILE = "counter_data.ser";
    private static final String TASKS_FILE = "created_tasks.ser";
    private static final String COMPLEX_TASKS_FILE = "complex_tasks.ser";

    public static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(TasksManagement.employeeTasks);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while saving data: " + e.getMessage());
        }
    }

    public static void saveCounterData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeInt(Employee.getCnt());
            oos.writeInt(Task.getCnt());
            System.out.println("Cnt saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while saving counter: " + e.getMessage());
        }
    }

    public static void saveCreatedTasks(List<Task> createdTasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(createdTasks);
            System.out.println("Created tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while saving created tasks: " + e.getMessage());
        }
    }

    public static void saveComplexTasks(List<Task> complexTasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMPLEX_TASKS_FILE))) {
            oos.writeObject(complexTasks);
            System.out.println("Created complex tasks saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while saving complex tasks: " + e.getMessage());
        }
    }

    public static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Map<Employee, List<Task>> loadedData = (Map<Employee, List<Task>>) ois.readObject();
            TasksManagement.employeeTasks.clear();
            TasksManagement.employeeTasks.putAll(loadedData);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading data: " + e.getMessage());
        }
    }

    public static void loadCounterData() {
        File file = new File(COUNTER_FILE);
        if (!file.exists()) {
            System.out.println("No saved cnt found.");
            return;
        }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COUNTER_FILE))) {
                Employee.setCnt(ois.readInt());
                Task.setCnt(ois.readInt());
            } catch (IOException e) {
                System.err.println("Error while loading counter: " + e.getMessage());
            }

    }

    public static List<Task> loadCreatedTasks() {
        File file = new File(TASKS_FILE);
        if (!file.exists()) {
            System.out.println("No saved tasks found. Starting with an empty list.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading created tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static List<Task> loadComplexTasks() {
        File file = new File(COMPLEX_TASKS_FILE);
        if (!file.exists()) {
            System.out.println("No saved complex tasks found. Starting with an empty list.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COMPLEX_TASKS_FILE))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while loading created tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
