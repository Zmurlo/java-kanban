package main.manager;

import main.task.Epic;
import main.task.Subtask;
import main.task.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();
    Task getTask(int id);
    Subtask getSubtask(int id);
    Epic getEpic(int id);
    List<Task> getTasks();
    List<Subtask> getSubtasks();

    void updateSubtask(Subtask subtask);

    void deleteSubtask(int id);

    List<Epic> getEpics();
    int addNewTask(Task task);
    int addNewSubtask(Subtask subtask);
    int addNewEpic(Epic epic);
    void updateTask(Task task);
    void deleteTask(int id);

    void updateEpic(Epic epic);

    void deleteEpic(int id);

    List<Subtask> getEpicSubtasks(int epicId);
}
