package main.manager;

import main.task.ask;

import java.util.ArrayList;

public interface HistoryManager {
    void add(ask task);
    ArrayList<Object> getHistory();
}