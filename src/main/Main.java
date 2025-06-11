public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаем задачи
        int task1 = manager.createTask(new Task("Task 1", "Description"));
        int task2 = manager.createTask(new Task("Task 2", "Description"));
        int epic1 = manager.createEpic(new Epic("Epic 1", "Description"));
        int subtask1 = manager.createSubtask(new Subtask("Subtask 1", "Description", epic1));
        int subtask2 = manager.createSubtask(new Subtask("Subtask 2", "Description", epic1));
        int subtask3 = manager.createSubtask(new Subtask("Subtask 3", "Description", epic1));
        int epic2 = manager.createEpic(new Epic("Epic 2", "Description"));

        // Симулируем просмотры
        manager.getTask(task1);
        printHistory(manager); // [Task 1]

        manager.getEpic(epic1);
        printHistory(manager); // [Task 1, Epic 1]

        manager.getSubtask(subtask1);
        printHistory(manager); // [Task 1, Epic 1, Subtask 1]

        manager.getTask(task1); // Повторный просмотр
        printHistory(manager); // [Epic 1, Subtask 1, Task 1]

        // Удаляем задачу
        manager.removeTask(task1);
        printHistory(manager); // [Epic 1, Subtask 1]

        // Удаляем эпик
        manager.removeEpic(epic1);
        printHistory(manager); // []
    }

    private static void printHistory(TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println("  " + task);
        }
    }
}