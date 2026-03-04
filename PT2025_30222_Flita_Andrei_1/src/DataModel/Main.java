package DataModel;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

import DataAccess.SerializationUtil;
import GraphicalUserInterface.InterfaceGUI;


// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SerializationUtil.loadData();
        SerializationUtil.loadCounterData();

        InterfaceGUI.MainInterfacePage();


    }
}