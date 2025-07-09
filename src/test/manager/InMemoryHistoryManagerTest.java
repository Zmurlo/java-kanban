package test.manager;

import main.manager.HistoryManager;
import main.manager.Managers;
import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
        task = new Task("Test", "Description", Status.NEW);
        task.setId(1);
    }

    @Test
    void shouldAddTasksToHistory() {
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldNotExceedMaxHistorySize() {
        for (int i = 0; i < 15; i++) {
            Task t = new Task("main.task.Task" + i, "Desc", Status.NEW);
            t.setId(i);
            historyManager.add(t);
        }
        assertEquals(10, historyManager.getHistory().size());
    }
}
