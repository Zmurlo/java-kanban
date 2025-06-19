package main;

import main.manager.Managers;
import main.manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        // Печатаем историю
        System.out.println("История просмотров:");
        for (Object t : manager.getHistory()) {
            System.out.println(t);
        }
    }
}

