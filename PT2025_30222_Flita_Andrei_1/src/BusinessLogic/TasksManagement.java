package BusinessLogic;

import DataAccess.SerializationUtil;
import DataModel.Employee;
import DataModel.Task;

import java.util.*;

public class TasksManagement {
    public static Map<Employee, List<Task>> employeeTasks = new HashMap<>();

    public static void addEmployee(Employee employee) {
       if (!employeeTasks.containsKey(employee)) {
           employeeTasks.put(employee, new ArrayList<>());
           SerializationUtil.saveData();
           SerializationUtil.saveCounterData();
       }
    }

    public static List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeTasks.keySet());
    }

    private static Employee getEmployeeById(int idEmployee) {
        for (Employee employee : employeeTasks.keySet()) {
            if (employee.getIdEmployee() == idEmployee) {
                return employee;
            }
        }
        return null;
    }

    public static void assignTaskToEmployee(int idEmployee, Task task) {
        Employee employee = getEmployeeById(idEmployee);

        if (employee != null) {
            employeeTasks.get(employee).add(task);
            SerializationUtil.saveData();
        } else {
            System.out.println("Employee with ID " + idEmployee + " was not found.");
        }
    }

    public static void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = getEmployeeById(idEmployee);
        if (employee == null) {
            System.out.println("Employee with ID " + idEmployee + " was not found.");
            return;
        }

        List<Task> tasks = employeeTasks.get(employee);
        if (tasks == null) {
            System.out.println("Employee doesn't have any tasks.");
            return;
        }

        for (Task task : tasks) {
            if (task.getIdTask() == idTask) {
                String newStatus = task.getStatusTask().equals("Completed") ? "Uncompleted" : "Completed";
                    task.setStatusTask(newStatus);
                    SerializationUtil.saveData();
                System.out.println("Task status " + task.getNameTask() + "was modified to " + newStatus);
                return;
            }
        }
        System.out.println("Task with ID " + idTask + " wasn't found for employee " + employee.getName());
    }

    public static int calculateEmployeeWorkDuration(int idEmployee) {
        Employee employee = getEmployeeById(idEmployee);

        if (employee == null) {
            System.out.println("Employee with ID " + idEmployee + " was not found.");
            return 0;
        }

        List<Task> tasks = employeeTasks.get(employee);
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("Employee doesn't have any tasks.");
            return 0;
        }

        int totalDuration = 0;

        for (Task task : tasks) {
            if ("Completed".equals(task.getStatusTask())) {
                totalDuration += task.estimateDuration();
            }
        }

        System.out.println("Total work duration for employee " + employee.getName() + " is " + totalDuration + " hours.");
        return totalDuration;
    }

}

