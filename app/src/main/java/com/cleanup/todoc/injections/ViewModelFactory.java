package com.cleanup.todoc.injections;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.task.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;
    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context) {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(context);
                }
            }
        }
        return factory;
    }

    private ViewModelFactory(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        this.taskDataSource = new TaskDataRepository(database.taskDao());
        this.projectDataSource = new ProjectDataRepository(database.projectDao());
        this.executor = Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create( Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, projectDataSource, executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
