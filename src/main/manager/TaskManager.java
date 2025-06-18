package main.manager;

import main.task.Epic;
import main.task.Subtask;
import main.task.ask;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Object> getHistory();
    ask getTask(int id);
    Subtask getSubtask(int id);
    Epic getEpic(int id);
    List <ask> getTasks();
    List <Subtask> getSubtasks();

    void updateSubtask(Subtask subtask);

    void deleteSubtask(int id);

    List <Epic> getEpics();
    int addNewTask(ask task);
    int addNewSubtask(Subtask subtask);
    int addNewEpic(Epic epic);
    void updateTask(ask task);
    void deleteTask(int id);

    void updateEpic(Epic epic);

    void deleteEpic(int id);

    List <Subtask> getEpicSubtasks(int epicId);
}
