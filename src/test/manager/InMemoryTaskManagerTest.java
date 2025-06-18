package test.manager;

import main.manager.Managers;
import main.manager.TaskManager;
import main.task.Status;
import main.task.ask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    void shouldAddAndFindTaskById() {
        Task task = new Task("Test", "Description", Status.NEW);
        int taskId = manager.addNewTask(task);

        Task savedTask = manager.getTask(taskId);
        assertNotNull(savedTask);
        assertEquals(task, savedTask);
    }

    @Test
    void shouldSaveHistoryOfViewedTasks() {
        Task task = new Task("Test", "Desc", Status.NEW);
        int taskId = manager.addNewTask(task);

        manager.getTask(taskId);
        List<Task> history = manager.getHistory();

        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }
}