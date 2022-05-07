package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskDataRepository {
    private final TaskDao taskDao;
    private final Executor executor;

    // Constructeur à partir du DAO de TaskDao
    public TaskDataRepository(TaskDao taskDao, Executor executor) {
        this.taskDao = taskDao;
        this.executor = executor;
    }



    // Get
    public LiveData<List<Task>> getTasks(long taskId) {
        return this.taskDao.getTasks(taskId);
    }
    // Get all Projects
    public LiveData<List<Task>> getAllTasks() {
        return this.taskDao.getAllTasks();}
    // create
    public void createTask(Task task) {
        executor.execute(() -> { taskDao.insertTask(task);
        });
    }

    public void addTask(Task tasks){taskDao.insertTasks(tasks);}
    //UPDATE
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }
    //DELETE
    public void deleteTask(Long taskId){
        taskDao.deleteTask(taskId);
    }
}

