package DataModel;

import java.io.Serializable;

public class Employee implements Serializable {
    private static int cnt = 0;
    private final int idEmployee;
    private final String name;

    public Employee(String name) {
        this.idEmployee = ++cnt;
        this.name = name;
    }
    public int getIdEmployee() {return idEmployee;}
    public String getName() {return name;}

    public static int getCnt() { return cnt; }
    public static void setCnt(int value) { cnt = value; }
}
