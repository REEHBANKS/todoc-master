package com.cleanup.todoc.database.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)

public abstract class TodocDatabase extends RoomDatabase {
    private static volatile TodocDatabase INSTANCE;
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,TodocDatabase.class, "MyDatabase.db")
                            .addCallback(testDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static Callback testDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao()
                        .createProject(new Project(6L, "Projet Mars", 0xFFB4CDBA)));
            }
        };
    }
}
