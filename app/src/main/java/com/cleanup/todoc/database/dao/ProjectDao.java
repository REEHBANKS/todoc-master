package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

@Dao

public interface ProjectDao {

    /**
     * Method against duplicate projects with the same Id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    /**
     * Create a Project
     */
    void createProject (Project project);

    /**
     * get a project
     */
    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject (long projectId);
}

