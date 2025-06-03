import java.util.*;

public class TaskManager {
    private int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    // Методы для Task
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public int createTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    // Методы для Epic
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics() {
        epics.values().forEach(epic -> subtasks.keySet().removeAll(epic.getSubtaskIds()));
        epics.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public int createEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved != null) {
            saved.setName(epic.getName());
            saved.setDescription(epic.getDescription());
        }
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            subtasks.keySet().removeAll(epic.getSubtaskIds());
        }
    }

    // Методы для Subtask
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllSubtasks() {
        epics.values().forEach(epic -> {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        });
        subtasks.clear();
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public int createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) return -1;
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(epicId);
        epic.getSubtaskIds().add(subtask.getId());
        updateEpicStatus(epic);
        return subtask.getId();
    }

    public void updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        if (subtasks.containsKey(id)) {
            Subtask saved = subtasks.get(id);
            int oldEpicId = saved.getEpicId();
            int newEpicId = subtask.getEpicId();

            if (oldEpicId != newEpicId) {
                Epic oldEpic = epics.get(oldEpicId);
                if (oldEpic != null) {
                    oldEpic.getSubtaskIds().remove((Integer) id);
                    updateEpicStatus(oldEpic);
                }
                Epic newEpic = epics.get(newEpicId);
                if (newEpic != null) {
                    newEpic.getSubtaskIds().add(id);
                    updateEpicStatus(newEpic);
                }
            }
            subtasks.put(id, subtask);
            updateEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove((Integer) id);
                updateEpicStatus(epic);
            }
        }
    }

    public List<Subtask> getSubtasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return Collections.emptyList();
        return epic.getSubtaskIds().stream()
                .map(subtasks::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private void updateEpicStatus(Epic epic) {
        List<Subtask> subtasks = getSubtasksByEpic(epic.getId());
        if (subtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = subtasks.stream().allMatch(s -> s.getStatus() == TaskStatus.NEW);
        boolean allDone = subtasks.stream().allMatch(s -> s.getStatus() == TaskStatus.DONE);

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
