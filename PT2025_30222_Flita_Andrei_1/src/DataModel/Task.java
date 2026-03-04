package DataModel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits ComplexTask, SimpleTask {
    private static   int cnt = 0;
    private final int idTask;
    private final String nameTask;
    private String statusTask;

    public Task(String name, String status) {
        this.idTask = ++cnt;
        this.statusTask = status;
        this.nameTask = name;

    }
    public int getIdTask() {return idTask;}
    public void setStatusTask(String newStatus) {this.statusTask = newStatus;}
    public String getStatusTask() {return statusTask;}
    public String getNameTask() {return nameTask;}
    @Override
    public String toString() {
        return nameTask + " (" + statusTask + ")";
    }
    public abstract int estimateDuration();

    public static int getCnt() { return cnt; }
    public static void setCnt(int value) { cnt = value; }
}
