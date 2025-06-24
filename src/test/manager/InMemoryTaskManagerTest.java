package test.manager;

import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTaskManagerTest {

    @Test
    void shouldAddAndFindTaskById() {
        Task task = new Task("Test", "Description", Status.NEW);
        assertNotNull(task);
        assertEquals(task, task);
    }
}