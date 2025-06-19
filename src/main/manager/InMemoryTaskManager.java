package main.manager;

import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public ArrayList<Object> getHistory() {
        return historyManager.getHistory();
    }

}
