public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаем задачи
        Task task = new Task("Помыть посуду", "", Status.NEW);
        int taskId = manager.addNewTask(task);

        Epic epic = new Epic("Переезд", "Организовать переезд");
        int epicId = manager.addNewEpic(epic);

        Subtask subtask = new Subtask("Собрать коробки", "", Status.NEW, epicId);
        manager.addNewSubtask(subtask);

        // Просматриваем задачи
        manager.getTask(taskId);
        manager.getEpic(epicId);

        // Печатаем историю
        System.out.println("История просмотров:");
        for (Task t : manager.getHistory()) {
            System.out.println(t);
        }
    }
}

