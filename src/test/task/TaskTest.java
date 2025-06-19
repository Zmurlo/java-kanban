package test.task;

import main.task.Status;
import main.task.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("main.task.Task 1", "Description", Status.NEW);

        Task task2 = new Task("main.task.Task 2", "Description", Status.IN_PROGRESS);

        assertEquals(task1, task2);
    }
}
