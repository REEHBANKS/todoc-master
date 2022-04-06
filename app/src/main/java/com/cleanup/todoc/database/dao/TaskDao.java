package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao

public interface TaskDao {

    /**
     * get the list of tasks for a project
     */
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getItems(long projectId);

    /**
     *  to create a new task to do
     */
    @Insert
    long insertItem(Task task);

    /**
     * Update a task
     */
    @Update
    int updateItem(Task task);

    /**
     * Remove a task
     */
    @Query("DELETE FROM Task WHERE id = :itemId")
    int deleteItem(long itemId);

}
