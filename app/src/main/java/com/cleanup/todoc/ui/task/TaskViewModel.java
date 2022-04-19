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
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;

    }


    // FOR PROJECT
    public LiveData<List<Project>> getAllProject(){
        return projectDataSource.getAllProject();
    }

    // FOR TASK

    public LiveData<List<Task>> getTasks(long projectId) {
        return taskDataSource.getTasks(projectId);
    }

    public void createTask(long id, long projectId, @NonNull String name, long creationTimestamp) {
        executor.execute(() -> {
            taskDataSource.createTask(new Task(id, projectId, name, creationTimestamp));
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> taskDataSource.updateTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }

}
