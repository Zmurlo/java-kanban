package main.manager;

import main.task.Epic;
import main.task.Status;
import main.task.Subtask;
import main.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int nextId = 1;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    // Генерация нового ID
    private int generateId() {
        return nextId++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Методы для Task
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) historyManager.add(task);
        return task;
    }

    @Override
    public int addNewTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id); // Удаление из истории
    }

    // Методы для Subtask
    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) historyManager.add(subtask);
        return subtask;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            throw new IllegalArgumentException("Epic не существует");
        }

        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtask(id);
        updateEpicStatus(epic);

        return id;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            // Сохраняем предыдущий эпик для проверки изменений
            int oldEpicId = subtasks.get(subtask.getId()).getEpicId();

            subtasks.put(subtask.getId(), subtask);

            // Если изменился эпик, обновляем оба эпика
            if (oldEpicId != subtask.getEpicId()) {
                Epic oldEpic = epics.get(oldEpicId);
                if (oldEpic != null) {
                    oldEpic.removeSubtask(subtask.getId());
                    updateEpicStatus(oldEpic);
                }

                Epic newEpic = epics.get(subtask.getEpicId());
                if (newEpic != null) {
                    newEpic.addSubtask(subtask.getId());
                    updateEpicStatus(newEpic);
                }
            } else {
                Epic epic = epics.get(subtask.getEpicId());
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            historyManager.remove(id); // Удаление из истории

            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtask(id);
                updateEpicStatus(epic);
            }
        }
    }

    // Методы для Epic
    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) historyManager.add(epic);
        return epic;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());
            savedEpic.setName(epic.getName());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            // Удаляем все подзадачи эпика из истории
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            // Удаляем сам эпик из истории
            historyManager.remove(id);
        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);

        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        }

        return epicSubtasks;
    }

    // Обновление статуса Epic с защитой от изменений через сеттеры
    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtaskIds().isEmpty()) {
            epic.internalSetStatus(Status.NEW); // Используем внутренний метод
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) continue;

            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.internalSetStatus(Status.NEW);
        } else if (allDone) {
            epic.internalSetStatus(Status.DONE);
        } else {
            epic.internalSetStatus(Status.IN_PROGRESS);
        }
    }
}