//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Task 1", "Description 1", TaskStatus.NEW);
        int task1Id = manager.createTask(task1);
        Task task2 = new Task("Task 2", "Description 2", TaskStatus.IN_PROGRESS);
        int task2Id = manager.createTask(task2);

        // Создание эпика с подзадачами
        Epic epic1 = new Epic("Epic 1", "Epic 1 desc");
        int epic1Id = manager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask 1", "Sub 1", TaskStatus.NEW, epic1Id);
        int subtask1Id = manager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Subtask 2", "Sub 2", TaskStatus.NEW, epic1Id);
        int subtask2Id = manager.createSubtask(subtask2);

        Epic epic2 = new Epic("Epic 2", "Epic 2 desc");
        int epic2Id = manager.createEpic(epic2);
        Subtask subtask3 = new Subtask("Subtask 3", "Sub 3", TaskStatus.NEW, epic2Id);
        int subtask3Id = manager.createSubtask(subtask3);

        // Вывод списков
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());

        // Обновление статусов
        subtask1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask1);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subtask2);

        System.out.println("Epic 1 status: " + manager.getEpic(epic1Id).getStatus());

        // Удаление
        manager.deleteTask(task1Id);
        manager.deleteEpic(epic2Id);

        System.out.println("After deletion:");
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());
    }
}
