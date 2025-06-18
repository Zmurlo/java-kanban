package main.manager;

import main.task.ask;

import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<Object> history = new LinkedList<>().reversed();

    @Override
    public void add(ask task) {
        if (task == null) {
            return;
        }

        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        history.addLast(task);
    }

    @Override
    public ArrayList<Object> getHistory() {
        return new ArrayList<>(history);
    }
}
