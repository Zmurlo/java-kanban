package test.manager;

import main.manager.HistoryManager;
import main.manager.InMemoryHistoryManager;
import main.manager.Managers;
import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Task1", "Description", Status.NEW);
        task1.setId(1);
        task2 = new Task("Task2", "Description", Status.NEW);
        task2.setId(2);
    }

    @Test
    void shouldAddTasksToHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    @Test
    void shouldRemoveDuplicates() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1); // Дубликат

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task2, history.get(0));
        assertEquals(task1, history.get(1));
    }

    @Test
    void shouldRemoveFromBeginning() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }

    @Test
    void shouldRemoveFromMiddle() {
        Task task3 = new Task("Task3", "Desc", Status.NEW);
        task3.setId(3);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task3, history.get(1));
    }

    @Test
    void shouldRemoveFromEnd() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(2);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task1, history.get(0));
    }

    @Test
    void shouldHandleEmptyHistory() {
        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void shouldNotExceedUnlimitedSize() {
        for (int i = 0; i < 1000; i++) {
            Task task = new Task("Task" + i, "Desc", Status.NEW);
            task.setId(i);
            historyManager.add(task);
        }
        assertEquals(1000, historyManager.getHistory().size());
    }
}