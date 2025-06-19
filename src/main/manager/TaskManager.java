package main.manager;

import main.task.Epic;
import main.task.Subtask;
import main.task.ask;

import java.util.ArrayList;

public interface TaskManager {
    ArrayList<Object> getHistory();
    ask getTask(int id);
    int addNewTask(ask task);
    int addNewSubtask(Subtask subtask);
    int addNewEpic(Epic epic);
}
