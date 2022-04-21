package com.cleanup.todoc.ui.task;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskDataRepository taskDataRepository;
    private final ProjectDataRepository projectDataRepository;
    private final Executor executor;

    // DATA

    public TaskViewModel(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataRepository = taskDataRepository;
        this.projectDataRepository = projectDataSource;
        this.executor = executor;

    }


    // FOR All Projects
    public LiveData<List<Project>> getAllProject(){
        return projectDataRepository.getAllProject();
    }

    // For All Tasks
    public LiveData<List<Task>> getAllTasks(){
        return taskDataRepository.getAllTasks();
    }

    // FOR TASK

    public LiveData<List<Task>> getTasks(long projectId) {
        return taskDataRepository.getTasks(projectId);
    }

    public void createTask( long projectId, @NonNull String name, long creationTimestamp) {
        executor.execute(() -> {
            taskDataRepository.createTask(new Task( projectId, name, creationTimestamp));
        });
    }
    public void addTasks( long projectId, @NonNull String name, long creationTimestamp) {
        executor.execute(() -> {
            taskDataRepository.createTask(new Task( projectId, name, creationTimestamp));
        });
    }



    public void updateTask(Task task) {
        executor.execute(() -> taskDataRepository.updateTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataRepository.deleteTask(taskId));
    }

}
