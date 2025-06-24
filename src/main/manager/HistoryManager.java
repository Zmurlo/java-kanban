package main.manager;

import main.task.ask;

import java.util.List;

public interface HistoryManager {
    void add(ask task);
    void remove(int id);
    <Task> List<Task> getHistory();
}