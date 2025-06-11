package main.manager;

import main.task.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    void remove(int id); // Добавленный метод
    List<Task> getHistory();
}